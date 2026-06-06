package com.meetingmind.app.ui.meeting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.meetingmind.app.data.local.dao.SpeakerInfo
import com.meetingmind.app.data.remote.AiService
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.model.MeetingStatus
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.domain.repository.TranscriptRepository
import com.meetingmind.app.util.AudioPlayerManager
import com.meetingmind.app.util.DocumentExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val meetingRepository: MeetingRepository,
    private val transcriptRepository: TranscriptRepository,
    private val aiService: AiService,
    private val gson: Gson,
    private val documentExporter: DocumentExporter,
    val audioPlayer: AudioPlayerManager
) : ViewModel() {

    private val meetingId: Long = savedStateHandle["meetingId"] ?: 0L

    private val _uiState = MutableStateFlow(MeetingDetailUiState())
    val uiState: StateFlow<MeetingDetailUiState> = _uiState.asStateFlow()

    // Track if we have already triggered auto-start (to prevent re-triggering)
    private var autoStartTriggered = false

    init {
        loadMeeting()
        loadTranscripts()
        loadSpeakers()
    }

    private fun loadMeeting() {
        viewModelScope.launch {
            meetingRepository.getMeetingById(meetingId).collect { meeting ->
                val wasRecording = _uiState.value.meeting?.status == MeetingStatus.RECORDING
                val nowRecording = meeting?.status == MeetingStatus.RECORDING

                _uiState.value = _uiState.value.copy(meeting = meeting, isLoading = false)

                // Detect transition from RECORDING -> COMPLETED: trigger AI analysis automatically
                if (wasRecording && meeting?.status == MeetingStatus.COMPLETED) {
                    runAiAnalysis()
                }

                // Detect auto-start recording: meeting loaded with autoStart flag and is SCHEDULED
                if (!autoStartTriggered && meeting != null &&
                    meeting.autoStartRecording &&
                    meeting.status == MeetingStatus.SCHEDULED
                ) {
                    autoStartTriggered = true
                    _uiState.value = _uiState.value.copy(shouldAutoStartRecording = true)
                }
            }
        }
    }

    /**
     * Called by UI after auto-start recording has been consumed (navigation triggered)
     */
    fun onAutoStartConsumed() {
        _uiState.value = _uiState.value.copy(shouldAutoStartRecording = false)
    }

    fun setAutoStartRecording(enabled: Boolean) {
        viewModelScope.launch {
            meetingRepository.updateAutoStartRecording(meetingId, enabled)
        }
    }

    private fun loadTranscripts() {
        viewModelScope.launch {
            transcriptRepository.getSegmentsByMeetingId(meetingId).collect { segments ->
                _uiState.value = _uiState.value.copy(segments = segments)
            }
        }
    }

    private fun loadSpeakers() {
        viewModelScope.launch {
            val speakers = transcriptRepository.getSpeakersForMeeting(meetingId)
            _uiState.value = _uiState.value.copy(speakers = speakers)
        }
    }

    fun updateSegmentText(segmentId: Long, text: String) {
        viewModelScope.launch {
            transcriptRepository.updateSegmentText(segmentId, text)
        }
    }

    fun updateSpeakerName(label: String, name: String) {
        viewModelScope.launch {
            transcriptRepository.updateSpeakerName(meetingId, label, name)
            loadSpeakers()
        }
    }

    fun filterBySpeaker(speakerName: String?) {
        _uiState.value = _uiState.value.copy(selectedSpeakerFilter = speakerName)
    }

    fun generateAiContent() {
        viewModelScope.launch { runAiAnalysis() }
    }

    /**
     * Run full AI analysis pipeline:
     * 1. Polish transcript text
     * 2. Generate summary
     * 3. Extract todos
     * 4. Extract keywords
     */
    private suspend fun runAiAnalysis() {
        _uiState.value = _uiState.value.copy(isAiProcessing = true)
        val segments = transcriptRepository.getSegmentsByMeetingIdSync(meetingId)
        if (segments.isEmpty()) {
            _uiState.value = _uiState.value.copy(isAiProcessing = false)
            return
        }

        // Step 1: Polish transcript text
        val polishedSegments = aiService.polishTranscript(segments)
        if (polishedSegments.isNotEmpty()) {
            polishedSegments.forEach { (id, text) ->
                transcriptRepository.updateSegmentText(id, text)
            }
        }

        // Use polished segments for subsequent AI calls
        val finalSegments = if (polishedSegments.isNotEmpty()) {
            segments.map { seg ->
                polishedSegments[seg.id]?.let { seg.copy(text = it) } ?: seg
            }
        } else segments

        // Step 2: Generate summary
        val summary = aiService.generateSummary(finalSegments)
        summary?.let { meetingRepository.updateSummary(meetingId, it) }

        // Step 3: Extract todos
        val todos = aiService.extractTodos(finalSegments)
        if (todos.isNotEmpty()) {
            meetingRepository.updateTodos(meetingId, gson.toJson(todos))
        }

        // Step 4: Extract keywords
        val keywords = aiService.extractKeywords(finalSegments)
        if (keywords.isNotEmpty()) {
            meetingRepository.updateKeywords(meetingId, gson.toJson(keywords))
        }

        _uiState.value = _uiState.value.copy(isAiProcessing = false)
    }

    fun exportAs(format: String) {
        viewModelScope.launch {
            val meeting = _uiState.value.meeting ?: return@launch
            val segments = transcriptRepository.getSegmentsByMeetingIdSync(meetingId)
            
            val file = when (format) {
                "md" -> documentExporter.exportAsMarkdown(meeting, segments)
                "docx" -> documentExporter.exportAsWord(meeting, segments)
                "pdf" -> documentExporter.exportAsPdf(meeting, segments)
                else -> documentExporter.exportAsMarkdown(meeting, segments)
            }
            documentExporter.shareFile(file)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}

data class MeetingDetailUiState(
    val meeting: Meeting? = null,
    val segments: List<TranscriptSegment> = emptyList(),
    val speakers: List<SpeakerInfo> = emptyList(),
    val selectedSpeakerFilter: String? = null,
    val isLoading: Boolean = true,
    val isAiProcessing: Boolean = false,
    val shouldAutoStartRecording: Boolean = false
) {
    val filteredSegments: List<TranscriptSegment>
        get() = if (selectedSpeakerFilter != null) {
            segments.filter {
                it.speakerName == selectedSpeakerFilter || it.speakerLabel == selectedSpeakerFilter
            }
        } else {
            segments
        }
}
