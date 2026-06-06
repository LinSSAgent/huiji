package com.meetingmind.app.data.local.dao;

import androidx.room.*;
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J$\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\'J$\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u000eH\'J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u001a\u001a\u00020\u000eH\'J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u001a\u001a\u00020\u000eH\'J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010 J&\u0010!\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010$\u00a8\u0006%"}, d2 = {"Lcom/meetingmind/app/data/local/dao/TranscriptSegmentDao;", "", "deleteSegmentsByMeetingId", "", "meetingId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSegmentsByMeetingId", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/meetingmind/app/data/local/entity/TranscriptSegmentEntity;", "getSegmentsByMeetingIdSync", "getSegmentsBySpeaker", "speakerLabel", "", "getSegmentsBySpeakerName", "speakerName", "getSpeakersForMeeting", "Lcom/meetingmind/app/data/local/dao/SpeakerInfo;", "insertSegment", "segment", "(Lcom/meetingmind/app/data/local/entity/TranscriptSegmentEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSegments", "segments", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchMeetingIds", "query", "searchTranscripts", "updateSegment", "updateSegmentText", "segmentId", "text", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSpeakerName", "label", "name", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface TranscriptSegmentDao {
    
    @androidx.room.Query(value = "SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY startTimeMs ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity>> getSegmentsByMeetingId(long meetingId);
    
    @androidx.room.Query(value = "SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY startTimeMs ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSegmentsByMeetingIdSync(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM transcript_segments WHERE meetingId = :meetingId AND speakerLabel = :speakerLabel ORDER BY startTimeMs ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity>> getSegmentsBySpeaker(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String speakerLabel);
    
    @androidx.room.Query(value = "SELECT * FROM transcript_segments WHERE meetingId = :meetingId AND (speakerName = :speakerName OR speakerLabel = :speakerName) ORDER BY startTimeMs ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity>> getSegmentsBySpeakerName(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String speakerName);
    
    @androidx.room.Query(value = "SELECT DISTINCT speakerLabel, speakerName FROM transcript_segments WHERE meetingId = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSpeakersForMeeting(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSegment(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.entity.TranscriptSegmentEntity segment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSegments(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSegment(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.local.entity.TranscriptSegmentEntity segment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE transcript_segments SET text = :text, isEdited = 1 WHERE id = :segmentId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSegmentText(long segmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE transcript_segments SET speakerName = :name WHERE meetingId = :meetingId AND speakerLabel = :label")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSpeakerName(long meetingId, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM transcript_segments WHERE meetingId = :meetingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSegmentsByMeetingId(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT transcript_segments.* FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH :query")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.meetingmind.app.data.local.entity.TranscriptSegmentEntity>> searchTranscripts(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT DISTINCT meetingId FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH :query")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.Long>> searchMeetingIds(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
}