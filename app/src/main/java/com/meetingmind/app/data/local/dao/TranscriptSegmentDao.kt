package com.meetingmind.app.data.local.dao

import androidx.room.*
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptSegmentDao {

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY startTimeMs ASC")
    fun getSegmentsByMeetingId(meetingId: Long): Flow<List<TranscriptSegmentEntity>>

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY startTimeMs ASC")
    suspend fun getSegmentsByMeetingIdSync(meetingId: Long): List<TranscriptSegmentEntity>

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId AND speakerLabel = :speakerLabel ORDER BY startTimeMs ASC")
    fun getSegmentsBySpeaker(meetingId: Long, speakerLabel: String): Flow<List<TranscriptSegmentEntity>>

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId AND (speakerName = :speakerName OR speakerLabel = :speakerName) ORDER BY startTimeMs ASC")
    fun getSegmentsBySpeakerName(meetingId: Long, speakerName: String): Flow<List<TranscriptSegmentEntity>>

    @Query("SELECT DISTINCT speakerLabel, speakerName FROM transcript_segments WHERE meetingId = :meetingId")
    suspend fun getSpeakersForMeeting(meetingId: Long): List<SpeakerInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSegment(segment: TranscriptSegmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSegments(segments: List<TranscriptSegmentEntity>)

    @Update
    suspend fun updateSegment(segment: TranscriptSegmentEntity)

    @Query("UPDATE transcript_segments SET text = :text, isEdited = 1 WHERE id = :segmentId")
    suspend fun updateSegmentText(segmentId: Long, text: String)

    @Query("UPDATE transcript_segments SET speakerName = :name WHERE meetingId = :meetingId AND speakerLabel = :label")
    suspend fun updateSpeakerName(meetingId: Long, label: String, name: String)

    @Query("DELETE FROM transcript_segments WHERE meetingId = :meetingId")
    suspend fun deleteSegmentsByMeetingId(meetingId: Long)

    // FTS search
    @Query("SELECT transcript_segments.* FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH :query")
    fun searchTranscripts(query: String): Flow<List<TranscriptSegmentEntity>>

    @Query("SELECT DISTINCT meetingId FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH :query")
    fun searchMeetingIds(query: String): Flow<List<Long>>
}

data class SpeakerInfo(
    val speakerLabel: String,
    val speakerName: String?
)
