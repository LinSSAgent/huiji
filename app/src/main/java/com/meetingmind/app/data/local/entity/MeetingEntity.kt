package com.meetingmind.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val scheduledTime: Long? = null,
    val startTime: Long? = null,
    val endTime: Long? = null,
    val status: String = "SCHEDULED", // SCHEDULED, RECORDING, COMPLETED, CANCELLED
    val audioFilePath: String? = null,
    val summary: String? = null,
    val todos: String? = null, // JSON array
    val keywords: String? = null, // JSON array with annotations
    val autoStartRecording: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
