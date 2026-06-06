package com.meetingmind.app.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.meetingmind.app.MainActivity
import com.meetingmind.app.MeetingMindApp
import com.meetingmind.app.service.RecordingService

class MeetingReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val meetingId = intent.getLongExtra("meeting_id", -1)
        val meetingTitle = intent.getStringExtra("meeting_title") ?: "会议"
        val autoRecord = intent.getBooleanExtra("auto_record", false)

        if (autoRecord && meetingId != -1L) {
            // Auto start recording
            val recordIntent = Intent(context, RecordingService::class.java).apply {
                action = RecordingService.ACTION_START
                putExtra(RecordingService.EXTRA_MEETING_ID, meetingId)
            }
            context.startForegroundService(recordIntent)
        } else {
            // Show notification
            showReminderNotification(context, meetingId, meetingTitle)
        }
    }

    private fun showReminderNotification(context: Context, meetingId: Long, title: String) {
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("meeting_id", meetingId)
        }
        val openPendingIntent = PendingIntent.getActivity(
            context, meetingId.toInt(), openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Start recording action
        val recordIntent = Intent(context, RecordingService::class.java).apply {
            action = RecordingService.ACTION_START
            putExtra(RecordingService.EXTRA_MEETING_ID, meetingId)
        }
        val recordPendingIntent = PendingIntent.getForegroundService(
            context, meetingId.toInt() + 1000, recordIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, MeetingMindApp.CHANNEL_REMINDER)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("会议提醒")
            .setContentText("\"$title\" 即将开始")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(openPendingIntent)
            .addAction(
                android.R.drawable.ic_btn_speak_now,
                "开始录音",
                recordPendingIntent
            )
            .build()

        try {
            NotificationManagerCompat.from(context)
                .notify(meetingId.toInt(), notification)
        } catch (e: SecurityException) {
            // Permission not granted
        }
    }
}
