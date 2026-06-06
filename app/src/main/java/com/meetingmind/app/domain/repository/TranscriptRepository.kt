package com.meetingmind.app.domain.repository

import com.meetingmind.app.data.local.dao.SpeakerInfo
import com.meetingmind.app.domain.model.TranscriptSegment
import kotlinx.coroutines.flow.Flow

interface TranscriptRepository {
    fun getSegmentsByMeetingId(meetingId: Long): Flow<List<TranscriptSegment>>
    suspend fun getSegmentsByMeetingIdSync(meetingId: Long): List<TranscriptSegment>
    fun getSegmentsBySpeaker(meetingId: Long, speakerLabel: String): Flow<List<TranscriptSegment>>
    fun getSegmentsBySpeakerName(meetingId: Long, speakerName: String): Flow<List<TranscriptSegment>>
    suspend fun getSpeakersForMeeting(meetingId: Long): List<SpeakerInfo>
    suspend fun insertSegment(segment: TranscriptSegment): Long
    suspend fun insertSegments(segments: List<TranscriptSegment>)
    suspend fun updateSegmentText(segmentId: Long, text: String)
    suspend fun updateSpeakerName(meetingId: Long, label: String, name: String)
    suspend fun deleteSegmentsByMeetingId(meetingId: Long)
    fun searchTranscripts(query: String): Flow<List<TranscriptSegment>>
    fun searchMeetingIds(query: String): Flow<List<Long>>
}
