package com.meetingmind.app.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.util.MeetingScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository,
    private val meetingScheduler: MeetingScheduler
) : ViewModel() {

    private val _createdMeetingId = MutableStateFlow<Long?>(null)
    val createdMeetingId: StateFlow<Long?> = _createdMeetingId.asStateFlow()

    fun createMeeting(
        title: String,
        description: String?,
        scheduledTime: Long?
    ) {
        viewModelScope.launch {
            val meeting = Meeting(
                title = title,
                description = description,
                scheduledTime = scheduledTime
            )
            val id = meetingRepository.createMeeting(meeting)
            
            // Schedule reminder if time is set
            if (scheduledTime != null && scheduledTime > System.currentTimeMillis()) {
                meetingScheduler.scheduleMeetingReminder(
                    meetingId = id,
                    meetingTitle = title,
                    scheduledTime = scheduledTime
                )
            }
            
            _createdMeetingId.value = id
        }
    }
}
