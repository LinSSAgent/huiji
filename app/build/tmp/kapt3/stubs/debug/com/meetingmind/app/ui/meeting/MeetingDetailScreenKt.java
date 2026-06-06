package com.meetingmind.app.ui.meeting;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextOverflow;
import com.meetingmind.app.domain.model.MeetingStatus;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.util.AudioPlayerManager;
import com.meetingmind.app.util.TimeUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a \u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003H\u0003\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0003\u001a \u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u001a6\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00172\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007\u001a\u001c\u0010\u001b\u001a\u00020\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u001a \u0010\u001d\u001a\u00020\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u001aV\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010&2\u0018\u0010\'\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010&H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010)\u001ax\u0010*\u001a\u00020\u00012\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u000f2\b\u0010.\u001a\u0004\u0018\u00010\u00032\u0014\u0010/\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0001002\u0018\u00101\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010&2\u0018\u0010\'\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010&H\u0003\u001a\u0015\u00102\u001a\u00020$2\u0006\u00103\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\u00104\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00065"}, d2 = {"AiProcessingBanner", "", "text", "", "AiProcessingPlaceholder", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "subtitle", "AudioPlayerBar", "audioFilePath", "audioPlayer", "Lcom/meetingmind/app/util/AudioPlayerManager;", "KeywordTab", "keywords", "", "Lcom/meetingmind/app/domain/model/Keyword;", "isAiProcessing", "", "MeetingDetailScreen", "meetingId", "", "onNavigateBack", "Lkotlin/Function0;", "onStartRecording", "viewModel", "Lcom/meetingmind/app/ui/meeting/MeetingDetailViewModel;", "SummaryTab", "summary", "TodoTab", "todos", "Lcom/meetingmind/app/domain/model/TodoItem;", "TranscriptSegmentItem", "segment", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "speakerColor", "Landroidx/compose/ui/graphics/Color;", "onEdit", "Lkotlin/Function2;", "onRenameSpeaker", "TranscriptSegmentItem-RPmYEkk", "(Lcom/meetingmind/app/domain/model/TranscriptSegment;JLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "TranscriptTab", "segments", "speakers", "Lcom/meetingmind/app/data/local/dao/SpeakerInfo;", "selectedSpeaker", "onSpeakerFilter", "Lkotlin/Function1;", "onEditSegment", "getSpeakerColor", "speakerLabel", "(Ljava/lang/String;)J", "app_debug"})
public final class MeetingDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MeetingDetailScreen(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartRecording, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.ui.meeting.MeetingDetailViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TranscriptTab(java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments, java.util.List<com.meetingmind.app.data.local.dao.SpeakerInfo> speakers, java.lang.String selectedSpeaker, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSpeakerFilter, kotlin.jvm.functions.Function2<? super java.lang.Long, ? super java.lang.String, kotlin.Unit> onEditSegment, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRenameSpeaker) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SummaryTab(java.lang.String summary, boolean isAiProcessing) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TodoTab(java.util.List<com.meetingmind.app.domain.model.TodoItem> todos, boolean isAiProcessing) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void KeywordTab(java.util.List<com.meetingmind.app.domain.model.Keyword> keywords, boolean isAiProcessing) {
    }
    
    private static final long getSpeakerColor(java.lang.String speakerLabel) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AiProcessingPlaceholder(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String subtitle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AiProcessingBanner(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AudioPlayerBar(java.lang.String audioFilePath, com.meetingmind.app.util.AudioPlayerManager audioPlayer) {
    }
}