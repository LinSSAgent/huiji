package com.meetingmind.app.data.repository

import com.meetingmind.app.data.local.dao.MeetingDao
import com.meetingmind.app.data.local.entity.MeetingEntity
import com.meetingmind.app.domain.model.Keyword
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.model.MeetingStatus
import com.meetingmind.app.domain.model.TodoItem
import com.meetingmind.app.domain.repository.MeetingRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val meetingDao: MeetingDao,
    private val gson: Gson
) : MeetingRepository {

    override fun getAllMeetings(): Flow<List<Meeting>> {
        return meetingDao.getAllMeetings().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMeetingById(meetingId: Long): Flow<Meeting?> {
        return meetingDao.getMeetingByIdFlow(meetingId).map { it?.toDomain() }
    }

    override suspend fun getMeetingByIdSync(meetingId: Long): Meeting? {
        return meetingDao.getMeetingById(meetingId)?.toDomain()
    }

    override fun getScheduledMeetings(): Flow<List<Meeting>> {
        return meetingDao.getScheduledMeetings().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchMeetings(query: String): Flow<List<Meeting>> {
        return meetingDao.searchMeetings(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun createMeeting(meeting: Meeting): Long {
        return meetingDao.insertMeeting(meeting.toEntity())
    }

    override suspend fun updateMeeting(meeting: Meeting) {
        meetingDao.updateMeeting(meeting.toEntity())
    }

    override suspend fun deleteMeeting(meetingId: Long) {
        meetingDao.deleteMeetingById(meetingId)
    }

    override suspend fun startRecording(meetingId: Long) {
        meetingDao.startRecording(meetingId, System.currentTimeMillis())
    }

    override suspend fun stopRecording(meetingId: Long) {
        meetingDao.stopRecording(meetingId, System.currentTimeMillis())
    }

    override suspend fun updateAudioPath(meetingId: Long, path: String) {
        meetingDao.updateAudioPath(meetingId, path)
    }

    override suspend fun updateSummary(meetingId: Long, summary: String) {
        meetingDao.updateSummary(meetingId, summary)
    }

    override suspend fun updateTodos(meetingId: Long, todosJson: String) {
        meetingDao.updateTodos(meetingId, todosJson)
    }

    override suspend fun updateKeywords(meetingId: Long, keywordsJson: String) {
        meetingDao.updateKeywords(meetingId, keywordsJson)
    }

    private fun MeetingEntity.toDomain(): Meeting {
        val todoList: List<TodoItem> = if (todos != null) {
            try {
                gson.fromJson(todos, object : TypeToken<List<TodoItem>>() {}.type)
            } catch (e: Exception) {
                emptyList()
            }
        } else emptyList()

        val keywordList: List<Keyword> = if (keywords != null) {
            try {
                gson.fromJson(keywords, object : TypeToken<List<Keyword>>() {}.type)
            } catch (e: Exception) {
                emptyList()
            }
        } else emptyList()

        return Meeting(
            id = id,
            title = title,
            description = description,
            scheduledTime = scheduledTime,
            startTime = startTime,
            endTime = endTime,
            status = MeetingStatus.fromString(status),
            audioFilePath = audioFilePath,
            summary = summary,
            todos = todoList,
            keywords = keywordList,
            createdAt = createdAt
        )
    }

    private fun Meeting.toEntity(): MeetingEntity {
        return MeetingEntity(
            id = id,
            title = title,
            description = description,
            scheduledTime = scheduledTime,
            startTime = startTime,
            endTime = endTime,
            status = status.name,
            audioFilePath = audioFilePath,
            summary = summary,
            todos = if (todos.isNotEmpty()) gson.toJson(todos) else null,
            keywords = if (keywords.isNotEmpty()) gson.toJson(keywords) else null,
            createdAt = createdAt
        )
    }
}
