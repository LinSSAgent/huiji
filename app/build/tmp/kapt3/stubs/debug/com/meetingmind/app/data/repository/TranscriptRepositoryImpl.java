package com.meetingmind.app.data.repository;

import com.meetingmind.app.data.local.dao.SpeakerInfo;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ$\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J$\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0011H\u0016J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\f2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u001d\u001a\u00020\u0011H\u0016J\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u001d\u001a\u00020\u0011H\u0016J\u001e\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\"J&\u0010#\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010&J\f\u0010\'\u001a\u00020\r*\u00020(H\u0002J\f\u0010)\u001a\u00020(*\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/meetingmind/app/data/repository/TranscriptRepositoryImpl;", "Lcom/meetingmind/app/domain/repository/TranscriptRepository;", "transcriptSegmentDao", "Lcom/meetingmind/app/data/local/dao/TranscriptSegmentDao;", "(Lcom/meetingmind/app/data/local/dao/TranscriptSegmentDao;)V", "deleteSegmentsByMeetingId", "", "meetingId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSegmentsByMeetingId", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "getSegmentsByMeetingIdSync", "getSegmentsBySpeaker", "speakerLabel", "", "getSegmentsBySpeakerName", "speakerName", "getSpeakersForMeeting", "Lcom/meetingmind/app/data/local/dao/SpeakerInfo;", "insertSegment", "segment", "(Lcom/meetingmind/app/domain/model/TranscriptSegment;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSegments", "segments", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchMeetingIds", "query", "searchTranscripts", "updateSegmentText", "segmentId", "text", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSpeakerName", "label", "name", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomain", "Lcom/meetingmind/app/data/local/entity/TranscriptSegmentEntity;", "toEntity", "app_debug"})
public final class TranscriptRepositoryImpl implements com.meetingmind.app.domain.repository.TranscriptRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.data.local.dao.TranscriptSegmentDao transcriptSegmentDao = null;
    
    @javax.inject.Inject()
    public TranscriptRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.dao.TranscriptSegmentDao transcriptSegmentDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> getSegmentsByMeetingId(long meetingId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getSegmentsByMeetingIdSync(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> getSegmentsBySpeaker(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String speakerLabel) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> getSegmentsBySpeakerName(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String speakerName) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getSpeakersForMeeting(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertSegment(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.TranscriptSegment segment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertSegments(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateSegmentText(long segmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateSpeakerName(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteSegmentsByMeetingId(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> searchTranscripts(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<java.lang.Long>> searchMeetingIds(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    private final com.meetingmind.app.domain.model.TranscriptSegment toDomain(com.meetingmind.app.data.local.entity.TranscriptSegmentEntity $this$toDomain) {
        return null;
    }
    
    private final com.meetingmind.app.data.local.entity.TranscriptSegmentEntity toEntity(com.meetingmind.app.domain.model.TranscriptSegment $this$toEntity) {
        return null;
    }
}