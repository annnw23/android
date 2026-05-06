package com.spaceexplorer.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/spaceexplorer/data/dao/QuizResultDao;", "", "getTopResults", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/spaceexplorer/data/model/LeaderboardItem;", "insert", "", "result", "Lcom/spaceexplorer/data/entity/QuizResult;", "(Lcom/spaceexplorer/data/entity/QuizResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface QuizResultDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.spaceexplorer.data.entity.QuizResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "\n        SELECT qr.score, u.username, q.title as quizTitle, qr.timestamp\n        FROM quiz_results qr\n        INNER JOIN users u ON qr.userId = u.id\n        INNER JOIN quizzes q ON qr.quizId = q.id\n        ORDER BY qr.score DESC\n        LIMIT 10\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.spaceexplorer.data.model.LeaderboardItem>> getTopResults();
}