package com.spaceexplorer.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\f2\u0006\u0010\u0013\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000f\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0012\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\f0\u000bJ\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/spaceexplorer/data/repository/SpaceRepository;", "", "db", "Lcom/spaceexplorer/data/database/AppDatabase;", "(Lcom/spaceexplorer/data/database/AppDatabase;)V", "findOrCreateUser", "", "username", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLessons", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/spaceexplorer/data/entity/Lesson;", "getLessonById", "id", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestionsByQuizId", "Lcom/spaceexplorer/data/entity/QuizQuestion;", "quizId", "getQuizById", "Lcom/spaceexplorer/data/entity/Quiz;", "getQuizByLessonId", "lessonId", "getTopResults", "Lcom/spaceexplorer/data/model/LeaderboardItem;", "saveQuizResult", "", "result", "Lcom/spaceexplorer/data/entity/QuizResult;", "(Lcom/spaceexplorer/data/entity/QuizResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SpaceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.spaceexplorer.data.database.AppDatabase db = null;
    
    public SpaceRepository(@org.jetbrains.annotations.NotNull()
    com.spaceexplorer.data.database.AppDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.spaceexplorer.data.entity.Lesson>> getAllLessons() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLessonById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Lesson> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuizByLessonId(int lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Quiz> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuizById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Quiz> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuestionsByQuizId(int quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.spaceexplorer.data.entity.QuizQuestion>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.spaceexplorer.data.model.LeaderboardItem>> getTopResults() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveQuizResult(@org.jetbrains.annotations.NotNull()
    com.spaceexplorer.data.entity.QuizResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object findOrCreateUser(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}