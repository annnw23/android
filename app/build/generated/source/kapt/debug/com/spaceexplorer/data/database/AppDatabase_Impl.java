package com.spaceexplorer.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.spaceexplorer.data.dao.LessonDao;
import com.spaceexplorer.data.dao.LessonDao_Impl;
import com.spaceexplorer.data.dao.QuizDao;
import com.spaceexplorer.data.dao.QuizDao_Impl;
import com.spaceexplorer.data.dao.QuizQuestionDao;
import com.spaceexplorer.data.dao.QuizQuestionDao_Impl;
import com.spaceexplorer.data.dao.QuizResultDao;
import com.spaceexplorer.data.dao.QuizResultDao_Impl;
import com.spaceexplorer.data.dao.UserDao;
import com.spaceexplorer.data.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile LessonDao _lessonDao;

  private volatile QuizDao _quizDao;

  private volatile QuizQuestionDao _quizQuestionDao;

  private volatile QuizResultDao _quizResultDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(18) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `lessons` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `imageRes` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quizzes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lessonId` INTEGER NOT NULL, `title` TEXT NOT NULL, `passingScore` INTEGER NOT NULL, `points` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quiz_questions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quizId` INTEGER NOT NULL, `questionText` TEXT NOT NULL, `options` TEXT NOT NULL, `correctOption` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quiz_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `quizId` INTEGER NOT NULL, `score` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '78311d086075b3a75969c2ce8b475a5b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `lessons`");
        db.execSQL("DROP TABLE IF EXISTS `quizzes`");
        db.execSQL("DROP TABLE IF EXISTS `quiz_questions`");
        db.execSQL("DROP TABLE IF EXISTS `quiz_results`");
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
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(2);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.spaceexplorer.data.entity.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsLessons = new HashMap<String, TableInfo.Column>(5);
        _columnsLessons.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("imageRes", new TableInfo.Column("imageRes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLessons = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLessons = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLessons = new TableInfo("lessons", _columnsLessons, _foreignKeysLessons, _indicesLessons);
        final TableInfo _existingLessons = TableInfo.read(db, "lessons");
        if (!_infoLessons.equals(_existingLessons)) {
          return new RoomOpenHelper.ValidationResult(false, "lessons(com.spaceexplorer.data.entity.Lesson).\n"
                  + " Expected:\n" + _infoLessons + "\n"
                  + " Found:\n" + _existingLessons);
        }
        final HashMap<String, TableInfo.Column> _columnsQuizzes = new HashMap<String, TableInfo.Column>(5);
        _columnsQuizzes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("passingScore", new TableInfo.Column("passingScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuizzes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuizzes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuizzes = new TableInfo("quizzes", _columnsQuizzes, _foreignKeysQuizzes, _indicesQuizzes);
        final TableInfo _existingQuizzes = TableInfo.read(db, "quizzes");
        if (!_infoQuizzes.equals(_existingQuizzes)) {
          return new RoomOpenHelper.ValidationResult(false, "quizzes(com.spaceexplorer.data.entity.Quiz).\n"
                  + " Expected:\n" + _infoQuizzes + "\n"
                  + " Found:\n" + _existingQuizzes);
        }
        final HashMap<String, TableInfo.Column> _columnsQuizQuestions = new HashMap<String, TableInfo.Column>(5);
        _columnsQuizQuestions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("quizId", new TableInfo.Column("quizId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("questionText", new TableInfo.Column("questionText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("options", new TableInfo.Column("options", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("correctOption", new TableInfo.Column("correctOption", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuizQuestions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuizQuestions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuizQuestions = new TableInfo("quiz_questions", _columnsQuizQuestions, _foreignKeysQuizQuestions, _indicesQuizQuestions);
        final TableInfo _existingQuizQuestions = TableInfo.read(db, "quiz_questions");
        if (!_infoQuizQuestions.equals(_existingQuizQuestions)) {
          return new RoomOpenHelper.ValidationResult(false, "quiz_questions(com.spaceexplorer.data.entity.QuizQuestion).\n"
                  + " Expected:\n" + _infoQuizQuestions + "\n"
                  + " Found:\n" + _existingQuizQuestions);
        }
        final HashMap<String, TableInfo.Column> _columnsQuizResults = new HashMap<String, TableInfo.Column>(5);
        _columnsQuizResults.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizResults.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizResults.put("quizId", new TableInfo.Column("quizId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizResults.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizResults.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuizResults = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuizResults = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuizResults = new TableInfo("quiz_results", _columnsQuizResults, _foreignKeysQuizResults, _indicesQuizResults);
        final TableInfo _existingQuizResults = TableInfo.read(db, "quiz_results");
        if (!_infoQuizResults.equals(_existingQuizResults)) {
          return new RoomOpenHelper.ValidationResult(false, "quiz_results(com.spaceexplorer.data.entity.QuizResult).\n"
                  + " Expected:\n" + _infoQuizResults + "\n"
                  + " Found:\n" + _existingQuizResults);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "78311d086075b3a75969c2ce8b475a5b", "ce117dbc36cf81c9d32f3dc7b403de64");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","lessons","quizzes","quiz_questions","quiz_results");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `lessons`");
      _db.execSQL("DELETE FROM `quizzes`");
      _db.execSQL("DELETE FROM `quiz_questions`");
      _db.execSQL("DELETE FROM `quiz_results`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
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
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LessonDao.class, LessonDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuizDao.class, QuizDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuizQuestionDao.class, QuizQuestionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuizResultDao.class, QuizResultDao_Impl.getRequiredConverters());
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
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public LessonDao lessonDao() {
    if (_lessonDao != null) {
      return _lessonDao;
    } else {
      synchronized(this) {
        if(_lessonDao == null) {
          _lessonDao = new LessonDao_Impl(this);
        }
        return _lessonDao;
      }
    }
  }

  @Override
  public QuizDao quizDao() {
    if (_quizDao != null) {
      return _quizDao;
    } else {
      synchronized(this) {
        if(_quizDao == null) {
          _quizDao = new QuizDao_Impl(this);
        }
        return _quizDao;
      }
    }
  }

  @Override
  public QuizQuestionDao quizQuestionDao() {
    if (_quizQuestionDao != null) {
      return _quizQuestionDao;
    } else {
      synchronized(this) {
        if(_quizQuestionDao == null) {
          _quizQuestionDao = new QuizQuestionDao_Impl(this);
        }
        return _quizQuestionDao;
      }
    }
  }

  @Override
  public QuizResultDao quizResultDao() {
    if (_quizResultDao != null) {
      return _quizResultDao;
    } else {
      synchronized(this) {
        if(_quizResultDao == null) {
          _quizResultDao = new QuizResultDao_Impl(this);
        }
        return _quizResultDao;
      }
    }
  }
}
