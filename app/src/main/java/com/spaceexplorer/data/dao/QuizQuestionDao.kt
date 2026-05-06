package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.QuizQuestion

@Dao
interface QuizQuestionDao {
    @Query("SELECT * FROM quiz_questions WHERE quizId = :quizId")
    suspend fun getQuestionsByQuizId(quizId: Int): List<QuizQuestion>

    @Insert
    suspend fun insertAll(questions: List<QuizQuestion>)
}
