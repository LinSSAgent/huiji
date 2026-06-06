package com.meetingmind.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.meetingmind.app.data.local.dao.MeetingDao;
import com.meetingmind.app.data.local.dao.MeetingDao_Impl;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MeetingDatabase_Impl extends MeetingDatabase {
  private volatile MeetingDao _meetingDao;

  private volatile TranscriptSegmentDao _transcriptSegmentDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `meetings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `scheduledTime` INTEGER, `startTime` INTEGER, `endTime` INTEGER, `status` TEXT NOT NULL, `audioFilePath` TEXT, `summary` TEXT, `todos` TEXT, `keywords` TEXT, `autoStartRecording` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transcript_segments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `meetingId` INTEGER NOT NULL, `speakerLabel` TEXT NOT NULL, `speakerName` TEXT, `text` TEXT NOT NULL, `startTimeMs` INTEGER NOT NULL, `endTimeMs` INTEGER NOT NULL, `isEdited` INTEGER NOT NULL, FOREIGN KEY(`meetingId`) REFERENCES `meetings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transcript_segments_meetingId` ON `transcript_segments` (`meetingId`)");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `transcript_fts` USING FTS4(`text` TEXT NOT NULL, `speakerName` TEXT, content=`transcript_segments`)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_BEFORE_UPDATE BEFORE UPDATE ON `transcript_segments` BEGIN DELETE FROM `transcript_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_BEFORE_DELETE BEFORE DELETE ON `transcript_segments` BEGIN DELETE FROM `transcript_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_AFTER_UPDATE AFTER UPDATE ON `transcript_segments` BEGIN INSERT INTO `transcript_fts`(`docid`, `text`, `speakerName`) VALUES (NEW.`rowid`, NEW.`text`, NEW.`speakerName`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_AFTER_INSERT AFTER INSERT ON `transcript_segments` BEGIN INSERT INTO `transcript_fts`(`docid`, `text`, `speakerName`) VALUES (NEW.`rowid`, NEW.`text`, NEW.`speakerName`); END");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '714c25490a7fd427f096734492e552b7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `meetings`");
        db.execSQL("DROP TABLE IF EXISTS `transcript_segments`");
        db.execSQL("DROP TABLE IF EXISTS `transcript_fts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_BEFORE_UPDATE BEFORE UPDATE ON `transcript_segments` BEGIN DELETE FROM `transcript_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_BEFORE_DELETE BEFORE DELETE ON `transcript_segments` BEGIN DELETE FROM `transcript_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_AFTER_UPDATE AFTER UPDATE ON `transcript_segments` BEGIN INSERT INTO `transcript_fts`(`docid`, `text`, `speakerName`) VALUES (NEW.`rowid`, NEW.`text`, NEW.`speakerName`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_transcript_fts_AFTER_INSERT AFTER INSERT ON `transcript_segments` BEGIN INSERT INTO `transcript_fts`(`docid`, `text`, `speakerName`) VALUES (NEW.`rowid`, NEW.`text`, NEW.`speakerName`); END");
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsMeetings = new HashMap<String, TableInfo.Column>(13);
        _columnsMeetings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("scheduledTime", new TableInfo.Column("scheduledTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("startTime", new TableInfo.Column("startTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("audioFilePath", new TableInfo.Column("audioFilePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("summary", new TableInfo.Column("summary", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("todos", new TableInfo.Column("todos", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("keywords", new TableInfo.Column("keywords", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("autoStartRecording", new TableInfo.Column("autoStartRecording", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeetings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMeetings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMeetings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMeetings = new TableInfo("meetings", _columnsMeetings, _foreignKeysMeetings, _indicesMeetings);
        final TableInfo _existingMeetings = TableInfo.read(db, "meetings");
        if (!_infoMeetings.equals(_existingMeetings)) {
          return new RoomOpenHelper.ValidationResult(false, "meetings(com.meetingmind.app.data.local.entity.MeetingEntity).\n"
                  + " Expected:\n" + _infoMeetings + "\n"
                  + " Found:\n" + _existingMeetings);
        }
        final HashMap<String, TableInfo.Column> _columnsTranscriptSegments = new HashMap<String, TableInfo.Column>(8);
        _columnsTranscriptSegments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("meetingId", new TableInfo.Column("meetingId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("speakerLabel", new TableInfo.Column("speakerLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("speakerName", new TableInfo.Column("speakerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("startTimeMs", new TableInfo.Column("startTimeMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("endTimeMs", new TableInfo.Column("endTimeMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranscriptSegments.put("isEdited", new TableInfo.Column("isEdited", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTranscriptSegments = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTranscriptSegments.add(new TableInfo.ForeignKey("meetings", "CASCADE", "NO ACTION", Arrays.asList("meetingId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTranscriptSegments = new HashSet<TableInfo.Index>(1);
        _indicesTranscriptSegments.add(new TableInfo.Index("index_transcript_segments_meetingId", false, Arrays.asList("meetingId"), Arrays.asList("ASC")));
        final TableInfo _infoTranscriptSegments = new TableInfo("transcript_segments", _columnsTranscriptSegments, _foreignKeysTranscriptSegments, _indicesTranscriptSegments);
        final TableInfo _existingTranscriptSegments = TableInfo.read(db, "transcript_segments");
        if (!_infoTranscriptSegments.equals(_existingTranscriptSegments)) {
          return new RoomOpenHelper.ValidationResult(false, "transcript_segments(com.meetingmind.app.data.local.entity.TranscriptSegmentEntity).\n"
                  + " Expected:\n" + _infoTranscriptSegments + "\n"
                  + " Found:\n" + _existingTranscriptSegments);
        }
        final HashSet<String> _columnsTranscriptFts = new HashSet<String>(2);
        _columnsTranscriptFts.add("text");
        _columnsTranscriptFts.add("speakerName");
        final FtsTableInfo _infoTranscriptFts = new FtsTableInfo("transcript_fts", _columnsTranscriptFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `transcript_fts` USING FTS4(`text` TEXT NOT NULL, `speakerName` TEXT, content=`transcript_segments`)");
        final FtsTableInfo _existingTranscriptFts = FtsTableInfo.read(db, "transcript_fts");
        if (!_infoTranscriptFts.equals(_existingTranscriptFts)) {
          return new RoomOpenHelper.ValidationResult(false, "transcript_fts(com.meetingmind.app.data.local.entity.TranscriptFtsEntity).\n"
                  + " Expected:\n" + _infoTranscriptFts + "\n"
                  + " Found:\n" + _existingTranscriptFts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "714c25490a7fd427f096734492e552b7", "6433f332983c4776cba9a16e8582f68f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("transcript_fts", "transcript_segments");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "meetings","transcript_segments","transcript_fts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `meetings`");
      _db.execSQL("DELETE FROM `transcript_segments`");
      _db.execSQL("DELETE FROM `transcript_fts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MeetingDao.class, MeetingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TranscriptSegmentDao.class, TranscriptSegmentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MeetingDao meetingDao() {
    if (_meetingDao != null) {
      return _meetingDao;
    } else {
      synchronized(this) {
        if(_meetingDao == null) {
          _meetingDao = new MeetingDao_Impl(this);
        }
        return _meetingDao;
      }
    }
  }

  @Override
  public TranscriptSegmentDao transcriptSegmentDao() {
    if (_transcriptSegmentDao != null) {
      return _transcriptSegmentDao;
    } else {
      synchronized(this) {
        if(_transcriptSegmentDao == null) {
          _transcriptSegmentDao = new TranscriptSegmentDao_Impl(this);
        }
        return _transcriptSegmentDao;
      }
    }
  }
}
