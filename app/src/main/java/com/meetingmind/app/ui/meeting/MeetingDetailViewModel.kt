package com.meetingmind.app.ui.meeting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.meetingmind.app.data.local.dao.SpeakerInfo
import com.meetingmind.app.data.remote.AiService
import com.meetingmind.app.domain.model.Meeting
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

    init {
        loadMeeting()
        loadTranscripts()
        loadSpeakers()
    }

    private fun loadMeeting() {
        viewModelScope.launch {
            meetingRepository.getMeetingById(meetingId).collect { meeting ->
                _uiState.value = _uiState.value.copy(meeting = meeting, isLoading = false)
            }
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
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAiProcessing = true)
            val segments = transcriptRepository.getSegmentsByMeetingIdSync(meetingId)
            if (segments.isEmpty()) {
                _uiState.value = _uiState.value.copy(isAiProcessing = false)
                return@launch
            }

            // Generate summary
            val summary = aiService.generateSummary(segments)
            summary?.let { meetingRepository.updateSummary(meetingId, it) }

            // Extract todos
            val todos = aiService.extractTodos(segments)
            if (todos.isNotEmpty()) {
                meetingRepository.updateTodos(meetingId, gson.toJson(todos))
            }

            // Extract keywords
            val keywords = aiService.extractKeywords(segments)
            if (keywords.isNotEmpty()) {
                meetingRepository.updateKeywords(meetingId, gson.toJson(keywords))
            }

            _uiState.value = _uiState.value.copy(isAiProcessing = false)
        }
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
    val isAiProcessing: Boolean = false
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
