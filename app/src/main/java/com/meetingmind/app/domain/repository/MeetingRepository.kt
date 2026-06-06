package com.meetingmind.app.domain.repository

import com.meetingmind.app.domain.model.Meeting
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    fun getAllMeetings(): Flow<List<Meeting>>
    fun getMeetingById(meetingId: Long): Flow<Meeting?>
    suspend fun getMeetingByIdSync(meetingId: Long): Meeting?
    fun getScheduledMeetings(): Flow<List<Meeting>>
    fun searchMeetings(query: String): Flow<List<Meeting>>
    suspend fun createMeeting(meeting: Meeting): Long
    suspend fun updateMeeting(meeting: Meeting)
    suspend fun deleteMeeting(meetingId: Long)
    suspend fun startRecording(meetingId: Long)
    suspend fun stopRecording(meetingId: Long)
    suspend fun updateAudioPath(meetingId: Long, path: String)
    suspend fun updateSummary(meetingId: Long, summary: String)
    suspend fun updateTodos(meetingId: Long, todosJson: String)
    suspend fun updateKeywords(meetingId: Long, keywordsJson: String)
    suspend fun updateAutoStartRecording(meetingId: Long, autoStart: Boolean)
}
