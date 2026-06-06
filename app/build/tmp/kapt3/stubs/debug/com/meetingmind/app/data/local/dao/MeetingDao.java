package com.meetingmind.app.data.local.dao;

import androidx.room.*;
import com.meetingmind.app.data.local.entity.MeetingEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0015\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0016\u001a\u00020\u0012H\'J\u001e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\u001c\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010!\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\"\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010#\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010%\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\'"}, d2 = {"Lcom/meetingmind/app/data/local/dao/MeetingDao;", "", "deleteMeeting", "", "meeting", "Lcom/meetingmind/app/data/local/entity/MeetingEntity;", "(Lcom/meetingmind/app/data/local/entity/MeetingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMeetingById", "meetingId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMeetings", "Lkotlinx/coroutines/flow/Flow;", "", "getMeetingById", "getMeetingByIdFlow", "getMeetingsByStatus", "status", "", "getScheduledMeetings", "insertMeeting", "searchMeetings", "query", "startRecording", "startTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopRecording", "endTime", "updateAudioPath", "path", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateKeywords", "keywords", "updateMeeting", "updateMeetingStatus", "updateSummary", "summary", "updateTodos", "todos", "app_debug"})
@androidx.room.Dao()
public abstract interface MeetingDao {
    
    @androidx.room.Query(value = "SELECT * FROM meetings ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.MeetingEntity>> getAllMeetings();
    
    @androidx.room.Query(value = "SELECT * FROM meetings WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMeetingById(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.meetingmind.app.data.local.entity.MeetingEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM meetings WHERE id = :meetingId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.meetingmind.app.data.local.entity.MeetingEntity> getMeetingByIdFlow(long meetingId);
    
    @androidx.room.Query(value = "SELECT * FROM meetings WHERE status = :status ORDER BY scheduledTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.MeetingEntity>> getMeetingsByStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String status);
    
    @androidx.room.Query(value = "SELECT * FROM meetings WHERE scheduledTime IS NOT NULL AND status = \'SCHEDULED\' ORDER BY scheduledTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.MeetingEntity>> getScheduledMeetings();
    
    @androidx.room.Query(value = "SELECT * FROM meetings WHERE title LIKE \'%\' || :query || \'%\' OR description LIKE \'%\' || :query || \'%\' ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.MeetingEntity>> searchMeetings(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.entity.MeetingEntity meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.entity.MeetingEntity meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.entity.MeetingEntity meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM meetings WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMeetingById(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET status = :status WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMeetingStatus(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET startTime = :startTime, status = \'RECORDING\' WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startRecording(long meetingId, long startTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET endTime = :endTime, status = \'COMPLETED\' WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopRecording(long meetingId, long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET audioFilePath = :path WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAudioPath(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET summary = :summary WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSummary(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String summary, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET todos = :todos WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTodos(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String todos, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE meetings SET keywords = :keywords WHERE id = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateKeywords(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String keywords, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}