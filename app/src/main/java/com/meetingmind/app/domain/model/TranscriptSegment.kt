package com.meetingmind.app.domain.model

data class TranscriptSegment(
    val id: Long = 0,
    val meetingId: Long,
    val speakerLabel: String,
    val speakerName: String? = null,
    val text: String,
    val startTimeMs: Long,
    val endTimeMs: Long,
    val isEdited: Boolean = false
) {
    val displaySpeaker: String
        get() = speakerName ?: speakerLabel
}
