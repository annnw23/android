package com.spaceexplorer.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.spaceexplorer.data.entity.Quiz;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizDao_Impl implements QuizDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Quiz> __insertionAdapterOfQuiz;

  public QuizDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuiz = new EntityInsertionAdapter<Quiz>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quizzes` (`id`,`lessonId`,`title`,`passingScore`,`points`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quiz entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLessonId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        statement.bindLong(4, entity.getPassingScore());
        statement.bindLong(5, entity.getPoints());
      }
    };
  }

  @Override
  public Object insert(final Quiz quiz, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuiz.insertAndReturnId(quiz);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getQuizByLessonId(final int lessonId,
      final Continuation<? super Quiz> $completion) {
    final String _sql = "SELECT * FROM quizzes WHERE lessonId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, lessonId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Quiz>() {
      @Override
      @Nullable
      public Quiz call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLessonId = CursorUtil.getColumnIndexOrThrow(_cursor, "lessonId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfPassingScore = CursorUtil.getColumnIndexOrThrow(_cursor, "passingScore");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final Quiz _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpLessonId;
            _tmpLessonId = _cursor.getInt(_cursorIndexOfLessonId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpPassingScore;
            _tmpPassingScore = _cursor.getInt(_cursorIndexOfPassingScore);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            _result = new Quiz(_tmpId,_tmpLessonId,_tmpTitle,_tmpPassingScore,_tmpPoints);
          } else {
            _result = null;
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
  public Object getQuizById(final int id, final Continuation<? super Quiz> $completion) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Quiz>() {
      @Override
      @Nullable
      public Quiz call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLessonId = CursorUtil.getColumnIndexOrThrow(_cursor, "lessonId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfPassingScore = CursorUtil.getColumnIndexOrThrow(_cursor, "passingScore");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final Quiz _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpLessonId;
            _tmpLessonId = _cursor.getInt(_cursorIndexOfLessonId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpPassingScore;
            _tmpPassingScore = _cursor.getInt(_cursorIndexOfPassingScore);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            _result = new Quiz(_tmpId,_tmpLessonId,_tmpTitle,_tmpPassingScore,_tmpPoints);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
