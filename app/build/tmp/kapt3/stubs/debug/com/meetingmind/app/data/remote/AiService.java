package com.meetingmind.app.data.remote;

import android.util.Log;
import com.google.gson.Gson;
import com.meetingmind.app.domain.model.Keyword;
import com.meetingmind.app.domain.model.TodoItem;
import com.meetingmind.app.domain.model.TranscriptSegment;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\b\u0006\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0013\u001a\u00020\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0002J\u001e\u0010\u0014\u001a\u0004\u0018\u00010\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0002J*\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\b0\u00182\u0006\u0010\u001a\u001a\u00020\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0002J\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\f2\b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0002J(\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\b0\u00182\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/meetingmind/app/data/remote/AiService;", "", "qwenApiService", "Lcom/meetingmind/app/data/remote/QwenApiService;", "gson", "Lcom/google/gson/Gson;", "(Lcom/meetingmind/app/data/remote/QwenApiService;Lcom/google/gson/Gson;)V", "callQwen", "", "prompt", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractKeywords", "", "Lcom/meetingmind/app/domain/model/Keyword;", "segments", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractTodos", "Lcom/meetingmind/app/domain/model/TodoItem;", "formatTranscript", "generateSummary", "parseKeywords", "json", "parsePolishedResult", "", "", "result", "originalSegments", "parseTodos", "polishTranscript", "Companion", "app_debug"})
public final class AiService {
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.data.remote.QwenApiService qwenApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AiService";
    @org.jetbrains.annotations.NotNull()
    public static final com.meetingmind.app.data.remote.AiService.Companion Companion = null;
    
    @javax.inject.Inject()
    public AiService(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.remote.QwenApiService qwenApiService, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    /**
     * Polish raw transcript text to improve readability.
     * Returns a map of segment ID -> polished text.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object polishTranscript(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Map<java.lang.Long, java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Parse the polished transcript result from AI.
     * Expects lines like "1: 润色后的文本" or "1. 润色后的文本"
     */
    private final java.util.Map<java.lang.Long, java.lang.String> parsePolishedResult(java.lang.String result, java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> originalSegments) {
        return null;
    }
    
    /**
     * Generate meeting summary from transcript segments
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateSummary(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Extract todo items from transcript
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object extractTodos(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.domain.model.TodoItem>> $completion) {
        return null;
    }
    
    /**
     * Extract keywords from transcript
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object extractKeywords(@org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.meetingmind.app.domain.model.Keyword>> $completion) {
        return null;
    }
    
    private final java.lang.String formatTranscript(java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    private final java.lang.Object callQwen(java.lang.String prompt, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.util.List<com.meetingmind.app.domain.model.TodoItem> parseTodos(java.lang.String json) {
        return null;
    }
    
    private final java.util.List<com.meetingmind.app.domain.model.Keyword> parseKeywords(java.lang.String json) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/meetingmind/app/data/remote/AiService$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}