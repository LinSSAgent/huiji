package com.meetingmind.app.data.local.dao

import androidx.room.*
import com.meetingmind.app.data.local.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingDao {

    @Query("SELECT * FROM meetings ORDER BY createdAt DESC")
    fun getAllMeetings(): Flow<List<MeetingEntity>>

    @Query("SELECT * FROM meetings WHERE id = :meetingId")
    suspend fun getMeetingById(meetingId: Long): MeetingEntity?

    @Query("SELECT * FROM meetings WHERE id = :meetingId")
    fun getMeetingByIdFlow(meetingId: Long): Flow<MeetingEntity?>

    @Query("SELECT * FROM meetings WHERE status = :status ORDER BY scheduledTime ASC")
    fun getMeetingsByStatus(status: String): Flow<List<MeetingEntity>>

    @Query("SELECT * FROM meetings WHERE scheduledTime IS NOT NULL AND status = 'SCHEDULED' ORDER BY scheduledTime ASC")
    fun getScheduledMeetings(): Flow<List<MeetingEntity>>

    @Query("SELECT * FROM meetings WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchMeetings(query: String): Flow<List<MeetingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: MeetingEntity): Long

    @Update
    suspend fun updateMeeting(meeting: MeetingEntity)

    @Delete
    suspend fun deleteMeeting(meeting: MeetingEntity)

    @Query("DELETE FROM meetings WHERE id = :meetingId")
    suspend fun deleteMeetingById(meetingId: Long)

    @Query("UPDATE meetings SET status = :status WHERE id = :meetingId")
    suspend fun updateMeetingStatus(meetingId: Long, status: String)

    @Query("UPDATE meetings SET startTime = :startTime, status = 'RECORDING' WHERE id = :meetingId")
    suspend fun startRecording(meetingId: Long, startTime: Long)

    @Query("UPDATE meetings SET endTime = :endTime, status = 'COMPLETED' WHERE id = :meetingId")
    suspend fun stopRecording(meetingId: Long, endTime: Long)

    @Query("UPDATE meetings SET audioFilePath = :path WHERE id = :meetingId")
    suspend fun updateAudioPath(meetingId: Long, path: String)

    @Query("UPDATE meetings SET summary = :summary WHERE id = :meetingId")
    suspend fun updateSummary(meetingId: Long, summary: String)

    @Query("UPDATE meetings SET todos = :todos WHERE id = :meetingId")
    suspend fun updateTodos(meetingId: Long, todos: String)

    @Query("UPDATE meetings SET keywords = :keywords WHERE id = :meetingId")
    suspend fun updateKeywords(meetingId: Long, keywords: String)

    @Query("UPDATE meetings SET autoStartRecording = :autoStart WHERE id = :meetingId")
    suspend fun updateAutoStartRecording(meetingId: Long, autoStart: Boolean)
}
