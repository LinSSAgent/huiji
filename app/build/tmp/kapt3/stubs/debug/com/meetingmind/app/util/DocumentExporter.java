package com.meetingmind.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.meetingmind.app.domain.model.Keyword;
import com.meetingmind.app.domain.model.Meeting;
import com.meetingmind.app.domain.model.TodoItem;
import com.meetingmind.app.domain.model.TranscriptSegment;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u001c\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u001c\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u001c\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0006H\u0002J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001a"}, d2 = {"Lcom/meetingmind/app/util/DocumentExporter;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "exportDir", "Ljava/io/File;", "getExportDir", "()Ljava/io/File;", "buildMarkdownContent", "", "meeting", "Lcom/meetingmind/app/domain/model/Meeting;", "segments", "", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "buildPlainTextContent", "exportAsMarkdown", "exportAsPdf", "exportAsWord", "getMimeType", "file", "sanitizeFileName", "name", "shareFile", "", "app_debug"})
public final class DocumentExporter {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public DocumentExporter(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getExportDir() {
        return null;
    }
    
    /**
     * Export meeting as Markdown file
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File exportAsMarkdown(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    /**
     * Export meeting as Word (.docx) file
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File exportAsWord(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    /**
     * Export meeting as PDF file
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File exportAsPdf(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.model.Meeting meeting, @org.jetbrains.annotations.NotNull()
    java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    public final void shareFile(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
    }
    
    private final java.lang.String buildMarkdownContent(com.meetingmind.app.domain.model.Meeting meeting, java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    private final java.lang.String buildPlainTextContent(com.meetingmind.app.domain.model.Meeting meeting, java.util.List<com.meetingmind.app.domain.model.TranscriptSegment> segments) {
        return null;
    }
    
    private final java.lang.String sanitizeFileName(java.lang.String name) {
        return null;
    }
    
    private final java.lang.String getMimeType(java.io.File file) {
        return null;
    }
}