package com.spaceexplorer.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/spaceexplorer/data/dao/QuizDao;", "", "getQuizById", "Lcom/spaceexplorer/data/entity/Quiz;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuizByLessonId", "lessonId", "insert", "", "quiz", "(Lcom/spaceexplorer/data/entity/Quiz;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface QuizDao {
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE lessonId = :lessonId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizByLessonId(int lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Quiz> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Quiz> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.spaceexplorer.data.entity.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
}