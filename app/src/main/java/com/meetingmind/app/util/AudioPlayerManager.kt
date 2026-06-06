package com.meetingmind.app.util

import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayerManager @Inject constructor() {

    companion object {
        private const val TAG = "AudioPlayerManager"
    }

    private var mediaPlayer: MediaPlayer? = null
    private var positionUpdateJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _currentFilePath = MutableStateFlow<String?>(null)
    val currentFilePath: StateFlow<String?> = _currentFilePath.asStateFlow()

    /**
     * Start playing the audio file at the given path.
     * If already playing the same file, does nothing.
     * If playing a different file, stops and starts the new one.
     */
    fun play(filePath: String) {
        // If already playing the same file, toggle pause/resume instead
        if (_currentFilePath.value == filePath && mediaPlayer?.isPlaying == false) {
            resume()
            return
        }
        if (_currentFilePath.value == filePath && mediaPlayer?.isPlaying == true) {
            pause()
            return
        }

        stop()

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentPosition.value = _duration.value
                    stopPositionUpdates()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what extra=$extra")
                    _isPlaying.value = false
                    stopPositionUpdates()
                    true
                }
                prepare()
            }
            _duration.value = mediaPlayer?.duration?.toLong() ?: 0L
            _currentPosition.value = 0L
            _currentFilePath.value = filePath

            mediaPlayer?.start()
            _isPlaying.value = true
            startPositionUpdates()

            Log.d(TAG, "Playback started: duration=${_duration.value}ms")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to play audio: ${e.message}", e)
            _isPlaying.value = false
            _currentFilePath.value = null
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
                stopPositionUpdates()
                Log.d(TAG, "Playback paused at ${_currentPosition.value}ms")
            }
        }
    }

    fun resume() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                _isPlaying.value = true
                startPositionUpdates()
                Log.d(TAG, "Playback resumed from ${_currentPosition.value}ms")
            }
        }
    }

    fun seekTo(positionMs: Long) {
        mediaPlayer?.let {
            it.seekTo(positionMs.toInt())
            _currentPosition.value = positionMs
            if (!it.isPlaying) {
                // After seeking while paused, update position display
                Log.d(TAG, "Seeked to ${positionMs}ms (paused)")
            }
        }
    }

    fun stop() {
        stopPositionUpdates()
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
        _currentFilePath.value = null
    }

    /**
     * Call when the screen/app is destroyed to release resources.
     */
    fun release() {
        stop()
    }

    private fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        _currentPosition.value = it.currentPosition.toLong()
                    }
                }
                delay(100L) // Update 10 times per second
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }
}
