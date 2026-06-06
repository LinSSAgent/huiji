package com.meetingmind.app.di;

import android.content.Context;
import androidx.room.Room;
import com.meetingmind.app.data.local.MeetingDatabase;
import com.meetingmind.app.data.local.dao.MeetingDao;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\f"}, d2 = {"Lcom/meetingmind/app/di/DatabaseModule;", "", "()V", "provideDatabase", "Lcom/meetingmind/app/data/local/MeetingDatabase;", "context", "Landroid/content/Context;", "provideMeetingDao", "Lcom/meetingmind/app/data/local/dao/MeetingDao;", "database", "provideTranscriptSegmentDao", "Lcom/meetingmind/app/data/local/dao/TranscriptSegmentDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.meetingmind.app.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.data.local.MeetingDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.data.local.dao.MeetingDao provideMeetingDao(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.MeetingDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.data.local.dao.TranscriptSegmentDao provideTranscriptSegmentDao(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.MeetingDatabase database) {
        return null;
    }
}