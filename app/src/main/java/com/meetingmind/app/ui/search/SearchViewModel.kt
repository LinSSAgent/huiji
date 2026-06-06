package com.meetingmind.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.domain.repository.TranscriptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository,
    private val transcriptRepository: TranscriptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun search(query: String) {
        _uiState.value = _uiState.value.copy(query = query)

        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                meetingResults = emptyList(),
                transcriptResults = emptyList(),
                isSearching = false
            )
            return
        }

        searchJob = viewModelScope.launch {
            delay(300)
            _uiState.value = _uiState.value.copy(isSearching = true)

            val meetings = meetingRepository.searchMeetings(query).first()

            val transcriptSegments = try {
                transcriptRepository.searchTranscripts("*$query*").first()
            } catch (e: Exception) {
                emptyList()
            }

            _uiState.value = _uiState.value.copy(
                meetingResults = meetings,
                transcriptResults = transcriptSegments,
                isSearching = false
            )
        }
    }
}

data class SearchUiState(
    val query: String = "",
    val meetingResults: List<Meeting> = emptyList(),
    val transcriptResults: List<TranscriptSegment> = emptyList(),
    val isSearching: Boolean = false
)
