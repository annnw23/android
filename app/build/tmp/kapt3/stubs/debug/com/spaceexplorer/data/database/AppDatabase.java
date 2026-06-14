package com.spaceexplorer.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u000e\u0010\u0005\u001a\u00020\u0006H\u0082@\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&\u00a8\u0006\u0012"}, d2 = {"Lcom/spaceexplorer/data/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "lessonDao", "Lcom/spaceexplorer/data/dao/LessonDao;", "prepopulate", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "quizDao", "Lcom/spaceexplorer/data/dao/QuizDao;", "quizQuestionDao", "Lcom/spaceexplorer/data/dao/QuizQuestionDao;", "quizResultDao", "Lcom/spaceexplorer/data/dao/QuizResultDao;", "userDao", "Lcom/spaceexplorer/data/dao/UserDao;", "Companion", "PrepopulateCallback", "app_debug"})
@androidx.room.Database(entities = {com.spaceexplorer.data.entity.User.class, com.spaceexplorer.data.entity.Lesson.class, com.spaceexplorer.data.entity.Quiz.class, com.spaceexplorer.data.entity.QuizQuestion.class, com.spaceexplorer.data.entity.QuizResult.class}, version = 18, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.spaceexplorer.data.database.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.spaceexplorer.data.database.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spaceexplorer.data.dao.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spaceexplorer.data.dao.LessonDao lessonDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spaceexplorer.data.dao.QuizDao quizDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spaceexplorer.data.dao.QuizQuestionDao quizQuestionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spaceexplorer.data.dao.QuizResultDao quizResultDao();
    
    private final java.lang.Object prepopulate(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/spaceexplorer/data/database/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/spaceexplorer/data/database/AppDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.spaceexplorer.data.database.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/spaceexplorer/data/database/AppDatabase$PrepopulateCallback;", "Landroidx/room/RoomDatabase$Callback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "onDestructiveMigration", "runPopulate", "app_debug"})
    static final class PrepopulateCallback extends androidx.room.RoomDatabase.Callback {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Context context = null;
        
        public PrepopulateCallback(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            super();
        }
        
        @java.lang.Override()
        public void onCreate(@org.jetbrains.annotations.NotNull()
        androidx.sqlite.db.SupportSQLiteDatabase db) {
        }
        
        @java.lang.Override()
        public void onDestructiveMigration(@org.jetbrains.annotations.NotNull()
        androidx.sqlite.db.SupportSQLiteDatabase db) {
        }
        
        private final void runPopulate() {
        }
    }
}