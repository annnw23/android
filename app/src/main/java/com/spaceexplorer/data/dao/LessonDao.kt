package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY isCompleted ASC, id ASC")
    // ORDER BY isCompleted ASC → 0 (nieukończone) idą pierwsze, 1 (ukończone) na końcu
    // ORDER BY id ASC → w obrębie tej samej grupy zachowujemy oryginalną kolejność
    // Flow automatycznie emituje nową listę gdy dane się zmienią → UI odświeża się samo
    fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE id = :id")
    suspend fun getLessonById(id: Int): Lesson?

    @Insert
    suspend fun insert(lesson: Lesson): Long

    @Query("UPDATE lessons SET isCompleted = :isCompleted WHERE id = :lessonId")
    // bezpośredni UPDATE zamiast wczytywania całego obiektu i zapisywania go z powrotem
    // wydajniejsze — zmienia tylko jedną kolumnę w jednym wierszu
    suspend fun updateCompletedStatus(lessonId: Int, isCompleted: Boolean)
}
