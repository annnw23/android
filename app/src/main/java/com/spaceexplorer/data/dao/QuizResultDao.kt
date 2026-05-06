package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.QuizResult
import com.spaceexplorer.data.model.LeaderboardItem
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResult): Long

    @Query("""
        SELECT qr.score, u.username, q.title as quizTitle, qr.timestamp
        FROM quiz_results qr
        INNER JOIN users u ON qr.userId = u.id
        INNER JOIN quizzes q ON qr.quizId = q.id
        ORDER BY qr.score DESC
        LIMIT 10
    """)
    fun getTopResults(): Flow<List<LeaderboardItem>>
}
