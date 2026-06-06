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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\fH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\fH\u00c6\u0003JU\u0010\u001f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u00c6\u0001J\u0013\u0010 \u001a\u00020\f2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\nH\u00d6\u0001R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0012R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006%"}, d2 = {"Lcom/meetingmind/app/ui/meeting/MeetingDetailUiState;", "", "meeting", "Lcom/meetingmind/app/domain/model/Meeting;", "segments", "", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "speakers", "Lcom/meetingmind/app/data/local/dao/SpeakerInfo;", "selectedSpeakerFilter", "", "isLoading", "", "isAiProcessing", "(Lcom/meetingmind/app/domain/model/Meeting;Ljava/util/List;Ljava/util/List;Ljava/lang/String;ZZ)V", "filteredSegments", "getFilteredSegments", "()Ljava/util/List;", "()Z", "getMeeting", "()Lcom/meetingmind/app/domain/model/Meeting;", "getSegments", "getSelectedSpeakerFilter", "()Ljava/lang/String;", "getSpeakers", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class MeetingDetailUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.meetingmind.app.domain.model.Meeting meeting = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> speakers = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedSpeakerFilter = null;
    private final boolean isLoading = false;
    private final boolean isAiProcessing = false;
    
    public MeetingDetailUiState(@org.jetbrains.annotations.Nullable()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> speakers, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedSpeakerFilter, boolean isLoading, boolean isAiProcessing) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.meetingmind.app.domain.model.Meeting getMeeting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> getSegments() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> getSpeakers() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedSpeakerFilter() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isAiProcessing() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> getFilteredSegments() {
        return null;
    }
    
    public MeetingDetailUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.meetingmind.app.domain.model.Meeting component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.ui.meeting.MeetingDetailUiState copy(@org.jetbrains.annotations.Nullable()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> speakers, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedSpeakerFilter, boolean isLoading, boolean isAiProcessing) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}