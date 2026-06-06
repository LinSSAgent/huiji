package com.meetingmind.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.util.MeetingScheduler;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/meetingmind/app/receiver/BootReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "meetingRepository", "Lcom/meetingmind/app/domain/repository/MeetingRepository;", "getMeetingRepository", "()Lcom/meetingmind/app/domain/repository/MeetingRepository;", "setMeetingRepository", "(Lcom/meetingmind/app/domain/repository/MeetingRepository;)V", "meetingScheduler", "Lcom/meetingmind/app/util/MeetingScheduler;", "getMeetingScheduler", "()Lcom/meetingmind/app/util/MeetingScheduler;", "setMeetingScheduler", "(Lcom/meetingmind/app/util/MeetingScheduler;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class BootReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.meetingmind.app.domain.repository.MeetingRepository meetingRepository;
    @javax.inject.Inject()
    public com.meetingmind.app.util.MeetingScheduler meetingScheduler;
    
    public BootReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.domain.repository.MeetingRepository getMeetingRepository() {
        return null;
    }
    
    public final void setMeetingRepository(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.MeetingRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.util.MeetingScheduler getMeetingScheduler() {
        return null;
    }
    
    public final void setMeetingScheduler(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.util.MeetingScheduler p0) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
}