package com.meetingmind.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.util.MeetingScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var meetingRepository: MeetingRepository
    @Inject lateinit var meetingScheduler: MeetingScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule all pending meeting reminders
            CoroutineScope(Dispatchers.IO).launch {
                val scheduledMeetings = meetingRepository.getScheduledMeetings().first()
                scheduledMeetings.forEach { meeting ->
                    meeting.scheduledTime?.let { time ->
                        if (time > System.currentTimeMillis()) {
                            meetingScheduler.scheduleMeetingReminder(
                                meetingId = meeting.id,
                                meetingTitle = meeting.title,
                                scheduledTime = time
                            )
                        }
                    }
                }
            }
        }
    }
}
