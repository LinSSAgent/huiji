package com.meetingmind.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeetingMindApp : Application() {

    companion object {
        const val CHANNEL_RECORDING = "recording_channel"
        const val CHANNEL_REMINDER = "reminder_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val recordingChannel = NotificationChannel(
                CHANNEL_RECORDING,
                "录音服务",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "会议录音进行中"
            }

            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDER,
                "会议提醒",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "会议即将开始提醒"
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(recordingChannel)
            notificationManager.createNotificationChannel(reminderChannel)
        }
    }
}
