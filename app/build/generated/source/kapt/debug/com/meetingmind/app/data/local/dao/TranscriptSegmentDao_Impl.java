package com.meetingmind.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.meetingmind.app.data.local.entity.TranscriptSegmentEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TranscriptSegmentDao_Impl implements TranscriptSegmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TranscriptSegmentEntity> __insertionAdapterOfTranscriptSegmentEntity;

  private final EntityDeletionOrUpdateAdapter<TranscriptSegmentEntity> __updateAdapterOfTranscriptSegmentEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSegmentText;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSpeakerName;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSegmentsByMeetingId;

  public TranscriptSegmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTranscriptSegmentEntity = new EntityInsertionAdapter<TranscriptSegmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transcript_segments` (`id`,`meetingId`,`speakerLabel`,`speakerName`,`text`,`startTimeMs`,`endTimeMs`,`isEdited`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TranscriptSegmentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMeetingId());
        if (entity.getSpeakerLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSpeakerLabel());
        }
        if (entity.getSpeakerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSpeakerName());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        statement.bindLong(6, entity.getStartTimeMs());
        statement.bindLong(7, entity.getEndTimeMs());
        final int _tmp = entity.isEdited() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__updateAdapterOfTranscriptSegmentEntity = new EntityDeletionOrUpdateAdapter<TranscriptSegmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transcript_segments` SET `id` = ?,`meetingId` = ?,`speakerLabel` = ?,`speakerName` = ?,`text` = ?,`startTimeMs` = ?,`endTimeMs` = ?,`isEdited` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TranscriptSegmentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMeetingId());
        if (entity.getSpeakerLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSpeakerLabel());
        }
        if (entity.getSpeakerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSpeakerName());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        statement.bindLong(6, entity.getStartTimeMs());
        statement.bindLong(7, entity.getEndTimeMs());
        final int _tmp = entity.isEdited() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSegmentText = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE transcript_segments SET text = ?, isEdited = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSpeakerName = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE transcript_segments SET speakerName = ? WHERE meetingId = ? AND speakerLabel = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSegmentsByMeetingId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transcript_segments WHERE meetingId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSegment(final TranscriptSegmentEntity segment,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTranscriptSegmentEntity.insertAndReturnId(segment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSegments(final List<TranscriptSegmentEntity> segments,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTranscriptSegmentEntity.insert(segments);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSegment(final TranscriptSegmentEntity segment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTranscriptSegmentEntity.handle(segment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSegmentText(final long segmentId, final String text,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSegmentText.acquire();
        int _argIndex = 1;
        if (text == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, text);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, segmentId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSegmentText.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSpeakerName(final long meetingId, final String label, final String name,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSpeakerName.acquire();
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, name);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, meetingId);
        _argIndex = 3;
        if (label == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, label);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSpeakerName.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSegmentsByMeetingId(final long meetingId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSegmentsByMeetingId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, meetingId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSegmentsByMeetingId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TranscriptSegmentEntity>> getSegmentsByMeetingId(final long meetingId) {
    final String _sql = "SELECT * FROM transcript_segments WHERE meetingId = ? ORDER BY startTimeMs ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, meetingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transcript_segments"}, new Callable<List<TranscriptSegmentEntity>>() {
      @Override
      @NonNull
      public List<TranscriptSegmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMeetingId = CursorUtil.getColumnIndexOrThrow(_cursor, "meetingId");
          final int _cursorIndexOfSpeakerLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerLabel");
          final int _cursorIndexOfSpeakerName = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerName");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfStartTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMs");
          final int _cursorIndexOfEndTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMs");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final List<TranscriptSegmentEntity> _result = new ArrayList<TranscriptSegmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranscriptSegmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMeetingId;
            _tmpMeetingId = _cursor.getLong(_cursorIndexOfMeetingId);
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpStartTimeMs;
            _tmpStartTimeMs = _cursor.getLong(_cursorIndexOfStartTimeMs);
            final long _tmpEndTimeMs;
            _tmpEndTimeMs = _cursor.getLong(_cursorIndexOfEndTimeMs);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            _item = new TranscriptSegmentEntity(_tmpId,_tmpMeetingId,_tmpSpeakerLabel,_tmpSpeakerName,_tmpText,_tmpStartTimeMs,_tmpEndTimeMs,_tmpIsEdited);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSegmentsByMeetingIdSync(final long meetingId,
      final Continuation<? super List<TranscriptSegmentEntity>> $completion) {
    final String _sql = "SELECT * FROM transcript_segments WHERE meetingId = ? ORDER BY startTimeMs ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, meetingId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TranscriptSegmentEntity>>() {
      @Override
      @NonNull
      public List<TranscriptSegmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMeetingId = CursorUtil.getColumnIndexOrThrow(_cursor, "meetingId");
          final int _cursorIndexOfSpeakerLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerLabel");
          final int _cursorIndexOfSpeakerName = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerName");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfStartTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMs");
          final int _cursorIndexOfEndTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMs");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final List<TranscriptSegmentEntity> _result = new ArrayList<TranscriptSegmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranscriptSegmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMeetingId;
            _tmpMeetingId = _cursor.getLong(_cursorIndexOfMeetingId);
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpStartTimeMs;
            _tmpStartTimeMs = _cursor.getLong(_cursorIndexOfStartTimeMs);
            final long _tmpEndTimeMs;
            _tmpEndTimeMs = _cursor.getLong(_cursorIndexOfEndTimeMs);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            _item = new TranscriptSegmentEntity(_tmpId,_tmpMeetingId,_tmpSpeakerLabel,_tmpSpeakerName,_tmpText,_tmpStartTimeMs,_tmpEndTimeMs,_tmpIsEdited);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TranscriptSegmentEntity>> getSegmentsBySpeaker(final long meetingId,
      final String speakerLabel) {
    final String _sql = "SELECT * FROM transcript_segments WHERE meetingId = ? AND speakerLabel = ? ORDER BY startTimeMs ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, meetingId);
    _argIndex = 2;
    if (speakerLabel == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, speakerLabel);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transcript_segments"}, new Callable<List<TranscriptSegmentEntity>>() {
      @Override
      @NonNull
      public List<TranscriptSegmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMeetingId = CursorUtil.getColumnIndexOrThrow(_cursor, "meetingId");
          final int _cursorIndexOfSpeakerLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerLabel");
          final int _cursorIndexOfSpeakerName = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerName");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfStartTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMs");
          final int _cursorIndexOfEndTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMs");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final List<TranscriptSegmentEntity> _result = new ArrayList<TranscriptSegmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranscriptSegmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMeetingId;
            _tmpMeetingId = _cursor.getLong(_cursorIndexOfMeetingId);
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpStartTimeMs;
            _tmpStartTimeMs = _cursor.getLong(_cursorIndexOfStartTimeMs);
            final long _tmpEndTimeMs;
            _tmpEndTimeMs = _cursor.getLong(_cursorIndexOfEndTimeMs);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            _item = new TranscriptSegmentEntity(_tmpId,_tmpMeetingId,_tmpSpeakerLabel,_tmpSpeakerName,_tmpText,_tmpStartTimeMs,_tmpEndTimeMs,_tmpIsEdited);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TranscriptSegmentEntity>> getSegmentsBySpeakerName(final long meetingId,
      final String speakerName) {
    final String _sql = "SELECT * FROM transcript_segments WHERE meetingId = ? AND (speakerName = ? OR speakerLabel = ?) ORDER BY startTimeMs ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, meetingId);
    _argIndex = 2;
    if (speakerName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, speakerName);
    }
    _argIndex = 3;
    if (speakerName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, speakerName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transcript_segments"}, new Callable<List<TranscriptSegmentEntity>>() {
      @Override
      @NonNull
      public List<TranscriptSegmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMeetingId = CursorUtil.getColumnIndexOrThrow(_cursor, "meetingId");
          final int _cursorIndexOfSpeakerLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerLabel");
          final int _cursorIndexOfSpeakerName = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerName");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfStartTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMs");
          final int _cursorIndexOfEndTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMs");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final List<TranscriptSegmentEntity> _result = new ArrayList<TranscriptSegmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranscriptSegmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMeetingId;
            _tmpMeetingId = _cursor.getLong(_cursorIndexOfMeetingId);
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpStartTimeMs;
            _tmpStartTimeMs = _cursor.getLong(_cursorIndexOfStartTimeMs);
            final long _tmpEndTimeMs;
            _tmpEndTimeMs = _cursor.getLong(_cursorIndexOfEndTimeMs);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            _item = new TranscriptSegmentEntity(_tmpId,_tmpMeetingId,_tmpSpeakerLabel,_tmpSpeakerName,_tmpText,_tmpStartTimeMs,_tmpEndTimeMs,_tmpIsEdited);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSpeakersForMeeting(final long meetingId,
      final Continuation<? super List<SpeakerInfo>> $completion) {
    final String _sql = "SELECT DISTINCT speakerLabel, speakerName FROM transcript_segments WHERE meetingId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, meetingId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SpeakerInfo>>() {
      @Override
      @NonNull
      public List<SpeakerInfo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSpeakerLabel = 0;
          final int _cursorIndexOfSpeakerName = 1;
          final List<SpeakerInfo> _result = new ArrayList<SpeakerInfo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SpeakerInfo _item;
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            _item = new SpeakerInfo(_tmpSpeakerLabel,_tmpSpeakerName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TranscriptSegmentEntity>> searchTranscripts(final String query) {
    final String _sql = "SELECT transcript_segments.* FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transcript_segments",
        "transcript_fts"}, new Callable<List<TranscriptSegmentEntity>>() {
      @Override
      @NonNull
      public List<TranscriptSegmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMeetingId = CursorUtil.getColumnIndexOrThrow(_cursor, "meetingId");
          final int _cursorIndexOfSpeakerLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerLabel");
          final int _cursorIndexOfSpeakerName = CursorUtil.getColumnIndexOrThrow(_cursor, "speakerName");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfStartTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMs");
          final int _cursorIndexOfEndTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMs");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final List<TranscriptSegmentEntity> _result = new ArrayList<TranscriptSegmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranscriptSegmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMeetingId;
            _tmpMeetingId = _cursor.getLong(_cursorIndexOfMeetingId);
            final String _tmpSpeakerLabel;
            if (_cursor.isNull(_cursorIndexOfSpeakerLabel)) {
              _tmpSpeakerLabel = null;
            } else {
              _tmpSpeakerLabel = _cursor.getString(_cursorIndexOfSpeakerLabel);
            }
            final String _tmpSpeakerName;
            if (_cursor.isNull(_cursorIndexOfSpeakerName)) {
              _tmpSpeakerName = null;
            } else {
              _tmpSpeakerName = _cursor.getString(_cursorIndexOfSpeakerName);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpStartTimeMs;
            _tmpStartTimeMs = _cursor.getLong(_cursorIndexOfStartTimeMs);
            final long _tmpEndTimeMs;
            _tmpEndTimeMs = _cursor.getLong(_cursorIndexOfEndTimeMs);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            _item = new TranscriptSegmentEntity(_tmpId,_tmpMeetingId,_tmpSpeakerLabel,_tmpSpeakerName,_tmpText,_tmpStartTimeMs,_tmpEndTimeMs,_tmpIsEdited);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Long>> searchMeetingIds(final String query) {
    final String _sql = "SELECT DISTINCT meetingId FROM transcript_segments JOIN transcript_fts ON transcript_segments.rowid = transcript_fts.rowid WHERE transcript_fts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transcript_segments",
        "transcript_fts"}, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Long> _result = new ArrayList<Long>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Long _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getLong(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
