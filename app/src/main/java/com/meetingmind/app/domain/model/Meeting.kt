package com.meetingmind.app.domain.model

data class Meeting(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val scheduledTime: Long? = null,
    val startTime: Long? = null,
    val endTime: Long? = null,
    val status: MeetingStatus = MeetingStatus.SCHEDULED,
    val audioFilePath: String? = null,
    val summary: String? = null,
    val todos: List<TodoItem> = emptyList(),
    val keywords: List<Keyword> = emptyList(),
    val autoStartRecording: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    val duration: Long?
        get() = if (startTime != null && endTime != null) endTime - startTime else null
}

enum class MeetingStatus {
    SCHEDULED,
    RECORDING,
    COMPLETED,
    CANCELLED;

    companion object {
        fun fromString(value: String): MeetingStatus {
            return try {
                valueOf(value)
            } catch (e: Exception) {
                SCHEDULED
            }
        }
    }
}

data class TodoItem(
    val content: String,
    val assignee: String? = null,
    val isCompleted: Boolean = false
)

data class Keyword(
    val word: String,
    val description: String? = null,
    val count: Int = 1
)
