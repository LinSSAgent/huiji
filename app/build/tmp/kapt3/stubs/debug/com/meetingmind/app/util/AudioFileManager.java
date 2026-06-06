package com.meetingmind.app.util;

import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\rH\u0002J\u0016\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020\rJ\u0018\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\rH\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/meetingmind/app/util/AudioFileManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "audioDir", "Ljava/io/File;", "getAudioDir", "()Ljava/io/File;", "currentFile", "outputStream", "Ljava/io/FileOutputStream;", "totalDataSize", "", "cancelRecording", "", "createNewRecording", "meetingId", "", "deleteRecording", "path", "", "finishRecording", "getRecordingFile", "intToByteArray", "", "value", "shortToByteArray", "", "updateWavHeader", "file", "dataSize", "writeAudioData", "data", "size", "writeWavHeader", "out", "app_debug"})
public final class AudioFileManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private java.io.FileOutputStream outputStream;
    @org.jetbrains.annotations.Nullable()
    private java.io.File currentFile;
    private int totalDataSize = 0;
    
    @javax.inject.Inject()
    public AudioFileManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getAudioDir() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.io.File createNewRecording(long meetingId) {
        return null;
    }
    
    public final void writeAudioData(@org.jetbrains.annotations.NotNull()
    byte[] data, int size) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String finishRecording() {
        return null;
    }
    
    public final void cancelRecording() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getRecordingFile(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
        return null;
    }
    
    public final void deleteRecording(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
    
    private final void writeWavHeader(java.io.FileOutputStream out, int dataSize) {
    }
    
    private final void updateWavHeader(java.io.File file, int dataSize) {
    }
    
    private final byte[] intToByteArray(int value) {
        return null;
    }
    
    private final byte[] shortToByteArray(short value) {
        return null;
    }
}