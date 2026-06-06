package com.meetingmind.app.data.local.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = TranscriptSegmentEntity::class)
@Entity(tableName = "transcript_fts")
data class TranscriptFtsEntity(
    val text: String,
    val speakerName: String?
)
