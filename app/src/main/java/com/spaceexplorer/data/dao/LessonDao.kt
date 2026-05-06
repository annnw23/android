package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons")
    fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE id = :id")
    suspend fun getLessonById(id: Int): Lesson?

    @Insert
    suspend fun insert(lesson: Lesson): Long
}
