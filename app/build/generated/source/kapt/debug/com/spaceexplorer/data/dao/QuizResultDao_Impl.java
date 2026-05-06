package com.spaceexplorer.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.spaceexplorer.data.entity.QuizResult;
import com.spaceexplorer.data.model.LeaderboardItem;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizResultDao_Impl implements QuizResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizResult> __insertionAdapterOfQuizResult;

  public QuizResultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizResult = new EntityInsertionAdapter<QuizResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quiz_results` (`id`,`userId`,`quizId`,`score`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuizResult entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getQuizId());
        statement.bindLong(4, entity.getScore());
        statement.bindLong(5, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insert(final QuizResult result, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuizResult.insertAndReturnId(result);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LeaderboardItem>> getTopResults() {
    final String _sql = "\n"
            + "        SELECT qr.score, u.username, q.title as quizTitle, qr.timestamp\n"
            + "        FROM quiz_results qr\n"
            + "        INNER JOIN users u ON qr.userId = u.id\n"
            + "        INNER JOIN quizzes q ON qr.quizId = q.id\n"
            + "        ORDER BY qr.score DESC\n"
            + "        LIMIT 10\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quiz_results", "users",
        "quizzes"}, new Callable<List<LeaderboardItem>>() {
      @Override
      @NonNull
      public List<LeaderboardItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfScore = 0;
          final int _cursorIndexOfUsername = 1;
          final int _cursorIndexOfQuizTitle = 2;
          final int _cursorIndexOfTimestamp = 3;
          final List<LeaderboardItem> _result = new ArrayList<LeaderboardItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaderboardItem _item;
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpQuizTitle;
            if (_cursor.isNull(_cursorIndexOfQuizTitle)) {
              _tmpQuizTitle = null;
            } else {
              _tmpQuizTitle = _cursor.getString(_cursorIndexOfQuizTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new LeaderboardItem(_tmpScore,_tmpUsername,_tmpQuizTitle,_tmpTimestamp);
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
