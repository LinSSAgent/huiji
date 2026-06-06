package com.meetingmind.app.data.repository

import com.meetingmind.app.data.local.dao.SpeakerInfo
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.domain.repository.TranscriptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TranscriptRepositoryImpl @Inject constructor(
    private val transcriptSegmentDao: TranscriptSegmentDao
) : TranscriptRepository {

    override fun getSegmentsByMeetingId(meetingId: Long): Flow<List<TranscriptSegment>> {
        return transcriptSegmentDao.getSegmentsByMeetingId(meetingId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSegmentsByMeetingIdSync(meetingId: Long): List<TranscriptSegment> {
        return transcriptSegmentDao.getSegmentsByMeetingIdSync(meetingId).map { it.toDomain() }
    }

    override fun getSegmentsBySpeaker(meetingId: Long, speakerLabel: String): Flow<List<TranscriptSegment>> {
        return transcriptSegmentDao.getSegmentsBySpeaker(meetingId, speakerLabel).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getSegmentsBySpeakerName(meetingId: Long, speakerName: String): Flow<List<TranscriptSegment>> {
        return transcriptSegmentDao.getSegmentsBySpeakerName(meetingId, speakerName).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSpeakersForMeeting(meetingId: Long): List<SpeakerInfo> {
        return transcriptSegmentDao.getSpeakersForMeeting(meetingId)
    }

    override suspend fun insertSegment(segment: TranscriptSegment): Long {
        return transcriptSegmentDao.insertSegment(segment.toEntity())
    }

    override suspend fun insertSegments(segments: List<TranscriptSegment>) {
        transcriptSegmentDao.insertSegments(segments.map { it.toEntity() })
    }

    override suspend fun updateSegmentText(segmentId: Long, text: String) {
        transcriptSegmentDao.updateSegmentText(segmentId, text)
    }

    override suspend fun updateSpeakerName(meetingId: Long, label: String, name: String) {
        transcriptSegmentDao.updateSpeakerName(meetingId, label, name)
    }

    override suspend fun deleteSegmentsByMeetingId(meetingId: Long) {
        transcriptSegmentDao.deleteSegmentsByMeetingId(meetingId)
    }

    override fun searchTranscripts(query: String): Flow<List<TranscriptSegment>> {
        return transcriptSegmentDao.searchTranscripts(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchMeetingIds(query: String): Flow<List<Long>> {
        return transcriptSegmentDao.searchMeetingIds(query)
    }

    private fun TranscriptSegmentEntity.toDomain(): TranscriptSegment {
        return TranscriptSegment(
            id = id,
            meetingId = meetingId,
            speakerLabel = speakerLabel,
            speakerName = speakerName,
            text = text,
            startTimeMs = startTimeMs,
            endTimeMs = endTimeMs,
            isEdited = isEdited
        )
    }

    private fun TranscriptSegment.toEntity(): TranscriptSegmentEntity {
        return TranscriptSegmentEntity(
            id = id,
            meetingId = meetingId,
            speakerLabel = speakerLabel,
            speakerName = speakerName,
            text = text,
            startTimeMs = startTimeMs,
            endTimeMs = endTimeMs,
            isEdited = isEdited
        )
    }
}
