package com.spaceexplorer.data.repository

import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.Quiz
import com.spaceexplorer.data.entity.QuizQuestion
import com.spaceexplorer.data.entity.QuizResult
import com.spaceexplorer.data.entity.User
import com.spaceexplorer.data.model.LeaderboardItem
import kotlinx.coroutines.flow.Flow

class SpaceRepository(private val db: AppDatabase) {

    fun getAllLessons() = db.lessonDao().getAllLessons()

    suspend fun getLessonById(id: Int) = db.lessonDao().getLessonById(id)

    suspend fun toggleLessonCompleted(lessonId: Int, isCompleted: Boolean) {
        // repozytorium deleguje do DAO — ViewModel nie wie jak dokładnie
        // dane są zapisywane (mogłoby to być API zamiast Room)
        db.lessonDao().updateCompletedStatus(lessonId, isCompleted)
    }

    suspend fun getQuizByLessonId(lessonId: Int): Quiz? = db.quizDao().getQuizByLessonId(lessonId)

    suspend fun getQuizById(id: Int): Quiz? = db.quizDao().getQuizById(id)

    suspend fun getQuestionsByQuizId(quizId: Int): List<QuizQuestion> =
        db.quizQuestionDao().getQuestionsByQuizId(quizId)

    fun getTopResults(): Flow<List<LeaderboardItem>> = db.quizResultDao().getTopResults()

    suspend fun saveQuizResult(result: QuizResult) = db.quizResultDao().insert(result)

    suspend fun findOrCreateUser(username: String): Int {
        val existing = db.userDao().getUserByUsername(username)
        return existing?.id ?: db.userDao().insert(User(username = username)).toInt()
    }
}
