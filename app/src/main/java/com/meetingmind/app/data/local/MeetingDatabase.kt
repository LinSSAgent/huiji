package com.meetingmind.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meetingmind.app.data.local.dao.MeetingDao
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao
import com.meetingmind.app.data.local.entity.MeetingEntity
import com.meetingmind.app.data.local.entity.TranscriptFtsEntity
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity

@Database(
    entities = [
        MeetingEntity::class,
        TranscriptSegmentEntity::class,
        TranscriptFtsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MeetingDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao
    abstract fun transcriptSegmentDao(): TranscriptSegmentDao
}
