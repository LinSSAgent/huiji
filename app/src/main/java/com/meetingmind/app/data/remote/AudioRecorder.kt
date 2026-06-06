package com.meetingmind.app.data.remote

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Singleton
class AudioRecorder @Inject constructor() {

    companion object {
        const val SAMPLE_RATE = 16000
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
        val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT) * 2
    }

    private var audioRecord: AudioRecord? = null
    private var isRecording = false

    fun startRecording(): Flow<ByteArray> = flow {
        val bufferSize = BUFFER_SIZE
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            bufferSize
        )

        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
            throw IllegalStateException("AudioRecord initialization failed")
        }

        audioRecord?.startRecording()
        isRecording = true

        val buffer = ByteArray(bufferSize)
        try {
            while (coroutineContext.isActive && isRecording) {
                val readSize = audioRecord?.read(buffer, 0, bufferSize) ?: -1
                if (readSize > 0) {
                    emit(buffer.copyOf(readSize))
                }
            }
        } finally {
            stopInternal()
        }
    }.flowOn(Dispatchers.IO)

    fun stop() {
        isRecording = false
    }

    private fun stopInternal() {
        try {
            audioRecord?.stop()
            audioRecord?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            audioRecord = null
            isRecording = false
        }
    }

    fun getAmplitude(buffer: ByteArray): Float {
        var maxAmplitude = 0
        for (i in buffer.indices step 2) {
            if (i + 1 < buffer.size) {
                val sample = (buffer[i + 1].toInt() shl 8) or (buffer[i].toInt() and 0xFF)
                val abs = Math.abs(sample)
                if (abs > maxAmplitude) maxAmplitude = abs
            }
        }
        return maxAmplitude / 32768f
    }
}
