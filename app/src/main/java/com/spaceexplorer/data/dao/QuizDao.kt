package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.Quiz

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getQuizByLessonId(lessonId: Int): Quiz?

    @Query("SELECT * FROM quizzes WHERE id = :id")
    suspend fun getQuizById(id: Int): Quiz?

    @Insert
    suspend fun insert(quiz: Quiz): Long
}
