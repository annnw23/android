package com.spaceexplorer.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.spaceexplorer.data.entity.QuizQuestion;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizQuestionDao_Impl implements QuizQuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizQuestion> __insertionAdapterOfQuizQuestion;

  public QuizQuestionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizQuestion = new EntityInsertionAdapter<QuizQuestion>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quiz_questions` (`id`,`quizId`,`questionText`,`options`,`correctOption`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuizQuestion entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuizId());
        if (entity.getQuestionText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getQuestionText());
        }
        if (entity.getOptions() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getOptions());
        }
        statement.bindLong(5, entity.getCorrectOption());
      }
    };
  }

  @Override
  public Object insertAll(final List<QuizQuestion> questions,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuizQuestion.insert(questions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object getQuestionsByQuizId(final int quizId,
      final Continuation<? super List<QuizQuestion>> arg1) {
    final String _sql = "SELECT * FROM quiz_questions WHERE quizId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, quizId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuizQuestion>>() {
      @Override
      @NonNull
      public List<QuizQuestion> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuizId = CursorUtil.getColumnIndexOrThrow(_cursor, "quizId");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectOption = CursorUtil.getColumnIndexOrThrow(_cursor, "correctOption");
          final List<QuizQuestion> _result = new ArrayList<QuizQuestion>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuizQuestion _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpQuizId;
            _tmpQuizId = _cursor.getInt(_cursorIndexOfQuizId);
            final String _tmpQuestionText;
            if (_cursor.isNull(_cursorIndexOfQuestionText)) {
              _tmpQuestionText = null;
            } else {
              _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            }
            final String _tmpOptions;
            if (_cursor.isNull(_cursorIndexOfOptions)) {
              _tmpOptions = null;
            } else {
              _tmpOptions = _cursor.getString(_cursorIndexOfOptions);
            }
            final int _tmpCorrectOption;
            _tmpCorrectOption = _cursor.getInt(_cursorIndexOfCorrectOption);
            _item = new QuizQuestion(_tmpId,_tmpQuizId,_tmpQuestionText,_tmpOptions,_tmpCorrectOption);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
