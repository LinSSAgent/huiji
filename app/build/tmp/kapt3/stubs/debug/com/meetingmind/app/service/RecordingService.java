package com.meetingmind.app.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.meetingmind.app.MainActivity;
import com.meetingmind.app.MeetingMindApp;
import com.meetingmind.app.R;
import com.meetingmind.app.data.remote.AudioRecorder;
import com.meetingmind.app.data.remote.TingwuWebSocketClient;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import com.meetingmind.app.util.AudioFileManager;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0007\u0018\u0000 O2\u00020\u0001:\u0002OPB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010@\u001a\u00020AH\u0002J\u0012\u0010B\u001a\u00020C2\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\b\u0010F\u001a\u00020GH\u0016J\"\u0010H\u001a\u00020I2\b\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020IH\u0016J\u000e\u0010L\u001a\u00020G2\u0006\u0010M\u001a\u00020\fJ\u0006\u0010N\u001a\u00020GR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00060\u001aR\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u001e\u0010#\u001a\u00020$8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\f0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u0010\u0010+\u001a\u0004\u0018\u00010,X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u00100\u001a\u0002018\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0010\u00106\u001a\u0004\u0018\u00010,X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u00107\u001a\u0002088\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0014\u0010=\u001a\b\u0018\u00010>R\u00020?X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006Q"}, d2 = {"Lcom/meetingmind/app/service/RecordingService;", "Landroid/app/Service;", "()V", "_currentAmplitude", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isRecording", "", "_liveTranscripts", "", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "_recordingDuration", "", "audioFileManager", "Lcom/meetingmind/app/util/AudioFileManager;", "getAudioFileManager", "()Lcom/meetingmind/app/util/AudioFileManager;", "setAudioFileManager", "(Lcom/meetingmind/app/util/AudioFileManager;)V", "audioRecorder", "Lcom/meetingmind/app/data/remote/AudioRecorder;", "getAudioRecorder", "()Lcom/meetingmind/app/data/remote/AudioRecorder;", "setAudioRecorder", "(Lcom/meetingmind/app/data/remote/AudioRecorder;)V", "binder", "Lcom/meetingmind/app/service/RecordingService$RecordingBinder;", "currentAmplitude", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentAmplitude", "()Lkotlinx/coroutines/flow/StateFlow;", "currentMeetingId", "isRecording", "liveTranscripts", "getLiveTranscripts", "meetingRepository", "Lcom/meetingmind/app/domain/repository/MeetingRepository;", "getMeetingRepository", "()Lcom/meetingmind/app/domain/repository/MeetingRepository;", "setMeetingRepository", "(Lcom/meetingmind/app/domain/repository/MeetingRepository;)V", "recordingDuration", "getRecordingDuration", "recordingJob", "Lkotlinx/coroutines/Job;", "recordingStartTime", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "tingwuClient", "Lcom/meetingmind/app/data/remote/TingwuWebSocketClient;", "getTingwuClient", "()Lcom/meetingmind/app/data/remote/TingwuWebSocketClient;", "setTingwuClient", "(Lcom/meetingmind/app/data/remote/TingwuWebSocketClient;)V", "transcriptJob", "transcriptRepository", "Lcom/meetingmind/app/domain/repository/TranscriptRepository;", "getTranscriptRepository", "()Lcom/meetingmind/app/domain/repository/TranscriptRepository;", "setTranscriptRepository", "(Lcom/meetingmind/app/domain/repository/TranscriptRepository;)V", "wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "createNotification", "Landroid/app/Notification;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onDestroy", "", "onStartCommand", "", "flags", "startId", "startRecording", "meetingId", "stopRecording", "Companion", "RecordingBinder", "app_debug"})
public final class RecordingService extends android.app.Service {
    @javax.inject.Inject()
    public com.meetingmind.app.data.remote.AudioRecorder audioRecorder;
    @javax.inject.Inject()
    public com.meetingmind.app.data.remote.TingwuWebSocketClient tingwuClient;
    @javax.inject.Inject()
    public com.meetingmind.app.util.AudioFileManager audioFileManager;
    @javax.inject.Inject()
    public com.meetingmind.app.domain.repository.MeetingRepository meetingRepository;
    @javax.inject.Inject()
    public com.meetingmind.app.domain.repository.TranscriptRepository transcriptRepository;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.service.RecordingService.RecordingBinder binder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job recordingJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job transcriptJob;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock wakeLock;
    private long currentMeetingId = -1L;
    private long recordingStartTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isRecording = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRecording = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Float> _currentAmplitude = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Float> currentAmplitude = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> _liveTranscripts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> liveTranscripts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _recordingDuration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> recordingDuration = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RecordingService";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "com.meetingmind.app.START_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "com.meetingmind.app.STOP_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MEETING_ID = "meeting_id";
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final com.meetingmind.app.service.RecordingService.Companion Companion = null;
    
    public RecordingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.data.remote.AudioRecorder getAudioRecorder() {
        return null;
    }
    
    public final void setAudioRecorder(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.remote.AudioRecorder p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.data.remote.TingwuWebSocketClient getTingwuClient() {
        return null;
    }
    
    public final void setTingwuClient(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.data.remote.TingwuWebSocketClient p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.util.AudioFileManager getAudioFileManager() {
        return null;
    }
    
    public final void setAudioFileManager(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.util.AudioFileManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.domain.repository.MeetingRepository getMeetingRepository() {
        return null;
    }
    
    public final void setMeetingRepository(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.MeetingRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.meetingmind.app.domain.repository.TranscriptRepository getTranscriptRepository() {
        return null;
    }
    
    public final void setTranscriptRepository(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.TranscriptRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRecording() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Float> getCurrentAmplitude() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.meetingmind.app.domain.model.TranscriptSegment>> getLiveTranscripts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getRecordingDuration() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    public final void startRecording(long meetingId) {
    }
    
    public final void stopRecording() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/meetingmind/app/service/RecordingService$Companion;", "", "()V", "ACTION_START", "", "ACTION_STOP", "EXTRA_MEETING_ID", "NOTIFICATION_ID", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/meetingmind/app/service/RecordingService$RecordingBinder;", "Landroid/os/Binder;", "(Lcom/meetingmind/app/service/RecordingService;)V", "getService", "Lcom/meetingmind/app/service/RecordingService;", "app_debug"})
    public final class RecordingBinder extends android.os.Binder {
        
        public RecordingBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.meetingmind.app.service.RecordingService getService() {
            return null;
        }
    }
}