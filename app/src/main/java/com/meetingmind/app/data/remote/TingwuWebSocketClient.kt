package com.meetingmind.app.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.meetingmind.app.BuildConfig
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Alibaba Cloud Tingwu real-time speech recognition client
 * Uses WebSocket for streaming audio and receiving transcription results
 */
@Singleton
class TingwuWebSocketClient @Inject constructor(
    @Named("websocket") private val okHttpClient: OkHttpClient,
    private val gson: Gson
) {
    companion object {
        private const val TAG = "TingwuWebSocket"
        // Tingwu real-time transcription API endpoint
        private const val WS_URL = "wss://dashscope.aliyuncs.com/api-ws/v1/inference"
    }

    private var webSocket: WebSocket? = null
    private val _transcriptResults = Channel<TranscriptResult>(Channel.BUFFERED)
    val transcriptResults: Flow<TranscriptResult> = _transcriptResults.receiveAsFlow()

    private var taskId: String? = null
    // @Volatile ensures visibility across threads (WebSocket thread sets it, audio thread reads it)
    @Volatile private var isTaskStarted = false
    @Volatile private var isWebSocketOpen = false
    private var audioChunkCount = 0

    data class TranscriptResult(
        val text: String,
        val speakerId: String = "speaker_0",
        val isFinal: Boolean = false,
        val startTimeMs: Long = 0,
        val endTimeMs: Long = 0
    )

    fun connect() {
        val request = Request.Builder()
            .url(WS_URL)
            .addHeader("Authorization", "Bearer ${BuildConfig.DASHSCOPE_API_KEY}")
            .addHeader("X-DashScope-DataInspection", "enable")
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket connected, sending start task...")
                isWebSocketOpen = true
                sendStartTask()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "Received: $text")
                parseMessage(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d(TAG, "Received binary message")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closing: $code $reason")
                webSocket.close(1000, null)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closed: $code $reason")
                isWebSocketOpen = false
                isTaskStarted = false
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "WebSocket failure: code=${response?.code}, message=${t.message}", t)
                isWebSocketOpen = false
                isTaskStarted = false
                _transcriptResults.trySend(
                    TranscriptResult(
                        text = "[连接错误: ${t.message}]",
                        isFinal = true
                    )
                )
            }
        })
    }

    private fun sendStartTask() {
        taskId = UUID.randomUUID().toString().replace("-", "")

        val startMessage = JsonObject().apply {
            add("header", JsonObject().apply {
                addProperty("action", "run-task")
                addProperty("task_id", taskId)
                addProperty("streaming", "duplex")
            })
            add("payload", JsonObject().apply {
                addProperty("task_group", "audio")
                addProperty("task", "asr")
                addProperty("function", "recognition")
                addProperty("model", "paraformer-realtime-v2")
                add("parameters", JsonObject().apply {
                    addProperty("format", "pcm")
                    addProperty("sample_rate", 16000)
                    // Use semantic punctuation for better sentence splitting (meeting scenario)
                    addProperty("semantic_punctuation_enabled", true)
                    // Convert Chinese numbers to Arabic numerals
                    addProperty("inverse_text_normalization_enabled", true)
                    // Filter filler words (um, uh, etc.)
                    addProperty("disfluency_removal_enabled", true)
                    // Restrict to Chinese + English
                    add("language_hints", com.google.gson.JsonArray().apply {
                        add("zh")
                        add("en")
                    })
                })
                add("input", JsonObject())
            })
        }

        val jsonStr = gson.toJson(startMessage)
        Log.d(TAG, "Sending start task: $jsonStr")
        webSocket?.send(jsonStr)
        Log.d(TAG, "Start task sent, taskId=$taskId")
    }

    fun sendAudio(audioData: ByteArray) {
        // Only send audio after server confirms task has started
        if (!isTaskStarted) {
            Log.v(TAG, "Audio dropped: task not yet started, data size=${audioData.size}")
            return
        }
        audioChunkCount++

        // Log first 3 chunks in detail for diagnosis
        if (audioChunkCount <= 3) {
            // Check if audio is all zeros (no sound captured)
            val nonZeroCount = audioData.count { it != 0.toByte() }
            // Calculate 16-bit PCM RMS amplitude (little-endian, 2 bytes per sample)
            var sumSquares = 0L
            var maxSample = 0
            var i = 0
            while (i + 1 < audioData.size) {
                val sample = (audioData[i + 1].toInt() shl 8) or (audioData[i].toInt() and 0xFF)
                sumSquares += sample.toLong() * sample
                val abs = Math.abs(sample)
                if (abs > maxSample) maxSample = abs
                i += 2
            }
            val sampleCount = audioData.size / 2
            val rms = if (sampleCount > 0) Math.sqrt(sumSquares.toDouble() / sampleCount).toInt() else 0
            Log.d(TAG, "Audio chunk #$audioChunkCount: size=${audioData.size}, nonZeroBytes=$nonZeroCount/$sampleCount samples, maxSample=$maxSample, rms=$rms")
            if (maxSample == 0) {
                Log.e(TAG, "!! AUDIO IS ALL ZEROS - microphone may not be working or permission issue")
            } else if (maxSample < 50) {
                Log.w(TAG, "!! Audio extremely quiet (max=$maxSample) - check microphone")
            }
        } else if (audioChunkCount % 20 == 0) {
            // Periodic summary
            Log.d(TAG, "Audio sent: chunk #$audioChunkCount, size=${audioData.size} bytes")
        }

        webSocket?.send(audioData.toByteString())
    }

    suspend fun stopAndClose() {
        if (isTaskStarted) {
            Log.d(TAG, "Sending finish-task, total audio chunks sent: $audioChunkCount")
            // Send stop signal
            val stopMessage = JsonObject().apply {
                add("header", JsonObject().apply {
                    addProperty("action", "finish-task")
                    addProperty("task_id", taskId)
                    addProperty("streaming", "duplex")
                })
                add("payload", JsonObject().apply {
                    add("input", JsonObject())
                })
            }
            webSocket?.send(gson.toJson(stopMessage))
            Log.d(TAG, "Finish task sent, waiting for server to flush remaining results...")

            // Wait up to 5 seconds for server to finish processing buffered audio
            // so that final transcript results are not lost
            repeat(50) { // 50 * 100ms = 5 seconds max
                if (!isTaskStarted) return@repeat // task-finished received
                delay(100)
            }
            Log.d(TAG, "Wait complete, isTaskStarted=$isTaskStarted")
        }

        webSocket?.close(1000, "Recording stopped")
        webSocket = null
        isTaskStarted = false
        isWebSocketOpen = false
    }

    private fun parseMessage(text: String) {
        try {
            val json = gson.fromJson(text, JsonObject::class.java)
            val header = json.getAsJsonObject("header")
            val event = header?.get("event")?.asString

            when (event) {
                "task-started" -> {
                    Log.d(TAG, "Task started successfully, ready to receive audio")
                    audioChunkCount = 0
                    isTaskStarted = true
                }
                "result-generated" -> {
                    val payload = json.getAsJsonObject("payload")
                    val output = payload?.getAsJsonObject("output")
                    val sentence = output?.getAsJsonObject("sentence")

                    if (sentence != null) {
                        // Skip heartbeat packets
                        val isHeartbeat = sentence.get("heartbeat")?.asBoolean ?: false
                        if (isHeartbeat) {
                            Log.v(TAG, "Heartbeat packet, skipping")
                            return
                        }

                        val resultText = sentence.get("text")?.asString ?: ""
                        val beginTime = sentence.get("begin_time")?.asLong ?: 0L
                        // end_time can be null for intermediate results
                        val endTime = if (sentence.get("end_time")?.isJsonNull == true) 0L
                                      else sentence.get("end_time")?.asLong ?: 0L
                        val isSentenceEnd = sentence.get("sentence_end")?.asBoolean ?: false

                        if (resultText.isNotBlank()) {
                            Log.d(TAG, "Transcript [${if (isSentenceEnd) "FINAL" else "partial"}]: $resultText")
                            _transcriptResults.trySend(
                                TranscriptResult(
                                    text = resultText,
                                    speakerId = "speaker_0",
                                    isFinal = isSentenceEnd,
                                    startTimeMs = beginTime,
                                    endTimeMs = endTime
                                )
                            )
                        }
                    } else {
                        Log.w(TAG, "result-generated but no sentence object: $text")
                    }
                }
                "task-finished" -> {
                    Log.d(TAG, "Task finished")
                    isTaskStarted = false
                }
                "task-failed" -> {
                    val errorCode = header.get("error_code")?.asString ?: "unknown"
                    val message = header.get("error_message")?.asString ?: "Unknown error"
                    Log.e(TAG, "Task failed: code=$errorCode message=$message")
                    isTaskStarted = false
                    _transcriptResults.trySend(
                        TranscriptResult(text = "[转写错误: $message]", isFinal = true)
                    )
                }
                else -> {
                    Log.d(TAG, "Unhandled event: $event")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Parse message error: ${e.message}", e)
        }
    }

    fun isConnected(): Boolean = isTaskStarted
}
