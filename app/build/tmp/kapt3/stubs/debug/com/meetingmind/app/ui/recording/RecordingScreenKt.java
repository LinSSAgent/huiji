package com.meetingmind.app.ui.recording;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.core.content.ContextCompat;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.service.RecordingService;
import com.meetingmind.app.util.TimeUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0003\u001a \u0010\u000b\u001a\u00020\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a.\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u0016\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u001e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a \u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0015\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\u001f"}, d2 = {"AudioWaveform", "", "amplitude", "", "isRecording", "", "modifier", "Landroidx/compose/ui/Modifier;", "LiveTranscriptItem", "segment", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "LiveTranscriptList", "transcripts", "", "PermissionDeniedContent", "onRetry", "Lkotlin/Function0;", "onBack", "RecordingControlBar", "onStop", "RecordingScreen", "meetingId", "", "onNavigateBack", "RecordingStatusBar", "duration", "getSpeakerColor", "Landroidx/compose/ui/graphics/Color;", "speakerLabel", "", "(Ljava/lang/String;)J", "app_debug"})
public final class RecordingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RecordingScreen(long meetingId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RecordingStatusBar(boolean isRecording, long duration, float amplitude) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AudioWaveform(float amplitude, boolean isRecording, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LiveTranscriptList(java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> transcripts, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LiveTranscriptItem(com.meetingmind.app.domain.model.TranscriptSegment segment) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RecordingControlBar(kotlin.jvm.functions.Function0<kotlin.Unit> onStop) {
    }
    
    private static final long getSpeakerColor(java.lang.String speakerLabel) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionDeniedContent(androidx.compose.ui.Modifier modifier, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
}