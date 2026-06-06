package com.meetingmind.app.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.meetingmind.app.MainActivity
import com.meetingmind.app.MeetingMindApp
import com.meetingmind.app.R
import com.meetingmind.app.data.remote.AudioRecorder
import com.meetingmind.app.data.remote.TingwuWebSocketClient
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.domain.repository.TranscriptRepository
import com.meetingmind.app.util.AudioFileManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class RecordingService : Service() {

    @Inject lateinit var audioRecorder: AudioRecorder
    @Inject lateinit var tingwuClient: TingwuWebSocketClient
    @Inject lateinit var audioFileManager: AudioFileManager
    @Inject lateinit var meetingRepository: MeetingRepository
    @Inject lateinit var transcriptRepository: TranscriptRepository

    private val binder = RecordingBinder()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var recordingJob: Job? = null
    private var transcriptJob: Job? = null
    private var wakeLock: PowerManager.WakeLock? = null

    private var currentMeetingId: Long = -1
    private var recordingStartTime: Long = 0

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _currentAmplitude = MutableStateFlow(0f)
    val currentAmplitude: StateFlow<Float> = _currentAmplitude.asStateFlow()

    private val _liveTranscripts = MutableStateFlow<List<TranscriptSegment>>(emptyList())
    val liveTranscripts: StateFlow<List<TranscriptSegment>> = _liveTranscripts.asStateFlow()

    private val _recordingDuration = MutableStateFlow(0L)
    val recordingDuration: StateFlow<Long> = _recordingDuration.asStateFlow()

    inner class RecordingBinder : Binder() {
        fun getService(): RecordingService = this@RecordingService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val meetingId = intent.getLongExtra(EXTRA_MEETING_ID, -1)
                if (meetingId != -1L) {
                    startRecording(meetingId)
                }
            }
            ACTION_STOP -> stopRecording()
        }
        return START_STICKY
    }

    fun startRecording(meetingId: Long) {
        if (_isRecording.value) return

        currentMeetingId = meetingId
        recordingStartTime = System.currentTimeMillis()

        // Acquire wake lock
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "MeetingMind::RecordingWakeLock"
        ).apply { acquire(4 * 60 * 60 * 1000L) } // 4 hours max

        // Start foreground
        startForeground(NOTIFICATION_ID, createNotification())

        // Update meeting status
        serviceScope.launch {
            meetingRepository.startRecording(meetingId)
        }

        // Create audio file
        try {
            audioFileManager.createNewRecording(meetingId)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to create audio file: ${e.message}", e)
            stopRecording()
            return
        }

        // Connect to Tingwu (non-blocking, errors handled gracefully)
        try {
            tingwuClient.connect()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to connect to Tingwu: ${e.message}", e)
            // Continue without transcription if connection fails
        }

        // Start recording and streaming
        _isRecording.value = true
        
        recordingJob = serviceScope.launch {
            try {
                audioRecorder.startRecording().collect { audioData ->
                    // Save to local file
                    audioFileManager.writeAudioData(audioData, audioData.size)
                    
                    // Send to Tingwu for transcription (ignore errors)
                    try {
                        tingwuClient.sendAudio(audioData)
                    } catch (_: Exception) { }
                    
                    // Update amplitude for UI
                    _currentAmplitude.value = audioRecorder.getAmplitude(audioData)
                }
            } catch (e: SecurityException) {
                android.util.Log.e(TAG, "AudioRecord permission denied: ${e.message}", e)
                _isRecording.value = false
                audioFileManager.cancelRecording()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Recording error: ${e.message}", e)
                _isRecording.value = false
            }
        }

        // Listen for transcript results
        transcriptJob = serviceScope.launch {
            try {
                tingwuClient.transcriptResults.collect { result ->
                    if (result.text.startsWith("[")) {
                        // Error message: show in UI for user visibility, don't save to DB
                        android.util.Log.w(TAG, "Tingwu error received: ${result.text}")
                        val errorSegment = TranscriptSegment(
                            meetingId = meetingId,
                            speakerLabel = "system",
                            text = result.text,
                            startTimeMs = System.currentTimeMillis(),
                            endTimeMs = System.currentTimeMillis()
                        )
                        val currentList = _liveTranscripts.value.toMutableList()
                        currentList.add(errorSegment)
                        _liveTranscripts.value = currentList
                    } else if (result.isFinal && result.text.isNotBlank()) {
                        val segment = TranscriptSegment(
                            meetingId = meetingId,
                            speakerLabel = result.speakerId,
                            text = result.text,
                            startTimeMs = result.startTimeMs,
                            endTimeMs = result.endTimeMs
                        )

                        // Save to database
                        transcriptRepository.insertSegment(segment)

                        // Update live view
                        val currentList = _liveTranscripts.value.toMutableList()
                        currentList.add(segment)
                        _liveTranscripts.value = currentList
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Transcript collection error: ${e.message}", e)
            }
        }

        // Duration timer
        serviceScope.launch {
            while (_isRecording.value) {
                _recordingDuration.value = System.currentTimeMillis() - recordingStartTime
                delay(1000)
            }
        }
    }

    fun stopRecording() {
        if (!_isRecording.value) return

        _isRecording.value = false

        // Stop audio recording (no more audio will be sent)
        audioRecorder.stop()
        recordingJob?.cancel()

        // Cancel duration timer
        // (durationJob will exit naturally because _isRecording is false)

        serviceScope.launch {
            // Send finish-task and wait up to 5s for remaining transcript results
            tingwuClient.stopAndClose()

            // NOW cancel transcript job (after server has flushed final results)
            transcriptJob?.cancel()

            // Save audio file
            val audioPath = audioFileManager.finishRecording()
            audioPath?.let {
                meetingRepository.updateAudioPath(currentMeetingId, it)
            }
            meetingRepository.stopRecording(currentMeetingId)

            // Release wake lock
            wakeLock?.release()
            wakeLock = null

            // Clear live data (after all final results have been collected)
            _liveTranscripts.value = emptyList()
            _recordingDuration.value = 0L
            _currentAmplitude.value = 0f

            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, MeetingMindApp.CHANNEL_RECORDING)
            .setContentTitle("正在录音")
            .setContentText("会议录音进行中...")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        wakeLock?.release()
    }

    companion object {
        private const val TAG = "RecordingService"
        const val ACTION_START = "com.meetingmind.app.START_RECORDING"
        const val ACTION_STOP = "com.meetingmind.app.STOP_RECORDING"
        const val EXTRA_MEETING_ID = "meeting_id"
        private const val NOTIFICATION_ID = 1001
    }
}
