package com.meetingmind.app.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.meetingmind.app.receiver.MeetingReminderReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleMeetingReminder(
        meetingId: Long,
        meetingTitle: String,
        scheduledTime: Long,
        autoRecord: Boolean = false
    ) {
        val intent = Intent(context, MeetingReminderReceiver::class.java).apply {
            action = "com.meetingmind.app.MEETING_REMINDER"
            putExtra("meeting_id", meetingId)
            putExtra("meeting_title", meetingTitle)
            putExtra("auto_record", autoRecord)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            meetingId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule 1 minute before the meeting time
        val triggerTime = scheduledTime - 60_000L

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    // Fallback to inexact alarm
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // Cannot schedule exact alarm, use inexact
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    fun cancelMeetingReminder(meetingId: Long) {
        val intent = Intent(context, MeetingReminderReceiver::class.java).apply {
            action = "com.meetingmind.app.MEETING_REMINDER"
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            meetingId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}
