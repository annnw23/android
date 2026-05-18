package com.spaceexplorer.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/spaceexplorer/data/dao/LessonDao;", "", "getAllLessons", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/spaceexplorer/data/entity/Lesson;", "getLessonById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "lesson", "(Lcom/spaceexplorer/data/entity/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCompletedStatus", "", "lessonId", "isCompleted", "", "(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface LessonDao {
    
    @androidx.room.Query(value = "SELECT * FROM lessons ORDER BY isCompleted ASC, id ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.spaceexplorer.data.entity.Lesson>> getAllLessons();
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLessonById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spaceexplorer.data.entity.Lesson> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.spaceexplorer.data.entity.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "UPDATE lessons SET isCompleted = :isCompleted WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCompletedStatus(int lessonId, boolean isCompleted, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}