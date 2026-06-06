package com.meetingmind.app.data.repository;

import com.meetingmind.app.data.local.dao.MeetingDao;
import com.meetingmind.app.data.local.entity.MeetingEntity;
import com.meetingmind.app.domain.model.Keyword;
import com.meetingmind.app.domain.model.Meeting;
import com.meetingmind.app.domain.model.MeetingStatus;
import com.meetingmind.app.domain.model.TodoItem;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00120\u0011H\u0016J\u0018\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u00112\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00120\u0011H\u0016J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00120\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 H\u0096@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010$\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010\'\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u001dJ\f\u0010)\u001a\u00020\n*\u00020*H\u0002J\f\u0010+\u001a\u00020**\u00020\nH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/meetingmind/app/data/repository/MeetingRepositoryImpl;", "Lcom/meetingmind/app/domain/repository/MeetingRepository;", "meetingDao", "Lcom/meetingmind/app/data/local/dao/MeetingDao;", "gson", "Lcom/google/gson/Gson;", "(Lcom/meetingmind/app/data/local/dao/MeetingDao;Lcom/google/gson/Gson;)V", "createMeeting", "", "meeting", "Lcom/meetingmind/app/domain/model/Meeting;", "(Lcom/meetingmind/app/domain/model/Meeting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMeeting", "", "meetingId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMeetings", "Lkotlinx/coroutines/flow/Flow;", "", "getMeetingById", "getMeetingByIdSync", "getScheduledMeetings", "searchMeetings", "query", "", "startRecording", "stopRecording", "updateAudioPath", "path", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAutoStartRecording", "autoStart", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateKeywords", "keywordsJson", "updateMeeting", "updateSummary", "summary", "updateTodos", "todosJson", "toDomain", "Lcom/meetingmind/app/data/local/entity/MeetingEntity;", "toEntity", "app_debug"})
public final class MeetingRepositoryImpl implements com.meetingmind.app.domain.repository.MeetingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.data.local.dao.MeetingDao meetingDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    @javax.inject.Inject()
    public MeetingRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.dao.MeetingDao meetingDao, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> getAllMeetings() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.meetingmind.app.domain.model.Meeting> getMeetingById(long meetingId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMeetingByIdSync(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.meetingmind.app.domain.model.Meeting> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> getScheduledMeetings() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.Meeting>> searchMeetings(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object createMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateMeeting(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteMeeting(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object startRecording(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stopRecording(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateAudioPath(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateSummary(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String summary, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateTodos(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String todosJson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateKeywords(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String keywordsJson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateAutoStartRecording(long meetingId, boolean autoStart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.meetingmind.app.domain.model.Meeting toDomain(com.meetingmind.app.data.local.entity.MeetingEntity $this$toDomain) {
        return null;
    }
    
    private final com.meetingmind.app.data.local.entity.MeetingEntity toEntity(com.meetingmind.app.domain.model.Meeting $this$toEntity) {
        return null;
    }
}