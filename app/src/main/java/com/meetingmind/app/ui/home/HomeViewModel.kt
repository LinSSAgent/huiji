package com.meetingmind.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.repository.MeetingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMeetings()
    }

    private fun loadMeetings() {
        viewModelScope.launch {
            meetingRepository.getAllMeetings().collect { meetings ->
                _uiState.value = _uiState.value.copy(
                    meetings = meetings,
                    isLoading = false
                )
            }
        }
    }

    fun deleteMeeting(meetingId: Long) {
        viewModelScope.launch {
            meetingRepository.deleteMeeting(meetingId)
        }
    }
}

data class HomeUiState(
    val meetings: List<Meeting> = emptyList(),
    val isLoading: Boolean = true
)
