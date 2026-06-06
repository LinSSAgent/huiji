package com.meetingmind.app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.meetingmind.app.data.local.dao.MeetingDao;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import com.meetingmind.app.data.local.entity.MeetingEntity;
import com.meetingmind.app.data.local.entity.TranscriptFtsEntity;
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/meetingmind/app/data/local/MeetingDatabase;", "Landroidx/room/RoomDatabase;", "()V", "meetingDao", "Lcom/meetingmind/app/data/local/dao/MeetingDao;", "transcriptSegmentDao", "Lcom/meetingmind/app/data/local/dao/TranscriptSegmentDao;", "app_debug"})
@androidx.room.Database(entities = {com.meetingmind.app.data.local.entity.MeetingEntity.class, com.meetingmind.app.data.local.entity.TranscriptSegmentEntity.class, com.meetingmind.app.data.local.entity.TranscriptFtsEntity.class}, version = 1, exportSchema = false)
public abstract class MeetingDatabase extends androidx.room.RoomDatabase {
    
    public MeetingDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.meetingmind.app.data.local.dao.MeetingDao meetingDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.meetingmind.app.data.local.dao.TranscriptSegmentDao transcriptSegmentDao();
}