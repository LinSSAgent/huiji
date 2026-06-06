package com.meetingmind.app.di

import com.meetingmind.app.data.repository.MeetingRepositoryImpl
import com.meetingmind.app.data.repository.TranscriptRepositoryImpl
import com.meetingmind.app.domain.repository.MeetingRepository
import com.meetingmind.app.domain.repository.TranscriptRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMeetingRepository(
        impl: MeetingRepositoryImpl
    ): MeetingRepository

    @Binds
    @Singleton
    abstract fun bindTranscriptRepository(
        impl: TranscriptRepositoryImpl
    ): TranscriptRepository
}
