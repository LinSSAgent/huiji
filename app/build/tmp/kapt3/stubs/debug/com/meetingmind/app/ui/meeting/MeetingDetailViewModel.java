package com.meetingmind.app.ui.meeting;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.meetingmind.app.data.local.dao.SpeakerInfo;
import com.meetingmind.app.data.remote.AiService;
import com.meetingmind.app.domain.model.Meeting;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import com.meetingmind.app.util.AudioPlayerManager;
import com.meetingmind.app.util.DocumentExporter;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B?\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u001d2\b\u0010!\u001a\u0004\u0018\u00010\u001fJ\u0006\u0010\"\u001a\u00020\u001dJ\b\u0010#\u001a\u00020\u001dH\u0002J\b\u0010$\u001a\u00020\u001dH\u0002J\b\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u001dH\u0014J\u0016\u0010\'\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u001fJ\u0016\u0010*\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u001fR\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006-"}, d2 = {"Lcom/meetingmind/app/ui/meeting/MeetingDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "meetingRepository", "Lcom/meetingmind/app/domain/repository/MeetingRepository;", "transcriptRepository", "Lcom/meetingmind/app/domain/repository/TranscriptRepository;", "aiService", "Lcom/meetingmind/app/data/remote/AiService;", "gson", "Lcom/google/gson/Gson;", "documentExporter", "Lcom/meetingmind/app/util/DocumentExporter;", "audioPlayer", "Lcom/meetingmind/app/util/AudioPlayerManager;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/meetingmind/app/domain/repository/MeetingRepository;Lcom/meetingmind/app/domain/repository/TranscriptRepository;Lcom/meetingmind/app/data/remote/AiService;Lcom/google/gson/Gson;Lcom/meetingmind/app/util/DocumentExporter;Lcom/meetingmind/app/util/AudioPlayerManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/meetingmind/app/ui/meeting/MeetingDetailUiState;", "getAudioPlayer", "()Lcom/meetingmind/app/util/AudioPlayerManager;", "meetingId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "exportAs", "", "format", "", "filterBySpeaker", "speakerName", "generateAiContent", "loadMeeting", "loadSpeakers", "loadTranscripts", "onCleared", "updateSegmentText", "segmentId", "text", "updateSpeakerName", "label", "name", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MeetingDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.domain.repository.MeetingRepository meetingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.domain.repository.TranscriptRepository transcriptRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.data.remote.AiService aiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.util.DocumentExporter documentExporter = null;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.util.AudioPlayerManager audioPlayer = null;
    private final long meetingId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.meetingmind.app.ui.meeting.MeetingDetailUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.meetingmind.app.ui.meeting.MeetingDetailUiState> uiState = null;
    
    @javax.inject.Inject()
    public MeetingDetailViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.MeetingRepository meetingRepository, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.TranscriptRepository transcriptRepository, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.remote.AiService aiService, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.util.DocumentExporter documentExporter, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.util.AudioPlayerManager audioPlayer) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.util.AudioPlayerManager getAudioPlayer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.meetingmind.app.ui.meeting.MeetingDetailUiState> getUiState() {
        return null;
    }
    
    private final void loadMeeting() {
    }
    
    private final void loadTranscripts() {
    }
    
    private final void loadSpeakers() {
    }
    
    public final void updateSegmentText(long segmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void updateSpeakerName(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void filterBySpeaker(@org.jetbrains.annotations.Nullable()
    java.lang.String speakerName) {
    }
    
    public final void generateAiContent() {
    }
    
    public final void exportAs(@org.jetbrains.annotations.NotNull()
    java.lang.String format) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}