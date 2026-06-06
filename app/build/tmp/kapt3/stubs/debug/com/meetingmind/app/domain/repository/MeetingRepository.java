package com.meetingmind.app.domain.repository;

import com.meetingmind.app.domain.model.Meeting;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH&J\u0018\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\t\u001a\u00020\u0003H&J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH&J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0016\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0018J\u001e\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u00a6@\u00a2\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u001f\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010 \u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0018J\u001e\u0010\"\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006$"}, d2 = {"Lcom/meetingmind/app/domain/repository/MeetingRepository;", "", "createMeeting", "", "meeting", "Lcom/meetingmind/app/domain/model/Meeting;", "(Lcom/meetingmind/app/domain/model/Meeting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMeeting", "", "meetingId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMeetings", "Lkotlinx/coroutines/flow/Flow;", "", "getMeetingById", "getMeetingByIdSync", "getScheduledMeetings", "searchMeetings", "query", "", "startRecording", "stopRecording", "updateAudioPath", "path", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAutoStartRecording", "autoStart", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateKeywords", "keywordsJson", "updateMeeting", "updateSummary", "summary", "updateTodos", "todosJson", "app_debug"})
public abstract interface MeetingRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> getAllMeetings();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.meetingmind.app.domain.model.Meeting> getMeetingById(long meetingId);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMeetingByIdSync(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.meetingmind.app.domain.model.Meeting> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> getScheduledMeetings();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> searchMeetings(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMeeting(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startRecording(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopRecording(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAudioPath(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSummary(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String summary, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTodos(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String todosJson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateKeywords(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String keywordsJson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAutoStartRecording(long meetingId, boolean autoStart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}