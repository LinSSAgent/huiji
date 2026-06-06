package com.meetingmind.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transcript_segments",
    foreignKeys = [
        ForeignKey(
            entity = MeetingEntity::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("meetingId")]
)
data class TranscriptSegmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val meetingId: Long,
    val speakerLabel: String, // System assigned speaker ID (e.g., "speaker_0")
    val speakerName: String? = null, // User assigned name
    val text: String,
    val startTimeMs: Long,
    val endTimeMs: Long,
    val isEdited: Boolean = false
)
