package com.meetingmind.app.di

import android.content.Context
import androidx.room.Room
import com.meetingmind.app.data.local.MeetingDatabase
import com.meetingmind.app.data.local.dao.MeetingDao
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MeetingDatabase {
        return Room.databaseBuilder(
            context,
            MeetingDatabase::class.java,
            "meeting_mind_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMeetingDao(database: MeetingDatabase): MeetingDao {
        return database.meetingDao()
    }

    @Provides
    fun provideTranscriptSegmentDao(database: MeetingDatabase): TranscriptSegmentDao {
        return database.transcriptSegmentDao()
    }
}
