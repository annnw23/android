package com.celestial.viewport.data.repository

import com.celestial.viewport.data.dao.*
import com.celestial.viewport.data.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CelestialRepository @Inject constructor(
    private val userDao: UserDao,
    private val lessonDao: LessonDao,
    private val quizDao: QuizDao,
    private val quizQuestionDao: QuizQuestionDao,
    private val quizResultDao: QuizResultDao,
) {

    // ─── Users ─────────────────────────────────────────────────────────────
    fun getCurrentUser(): Flow<User?> = userDao.getUserById(1) // user id=1 is local player

    fun getAllUsersRanked(): Flow<List<User>> = userDao.getAllUsersRanked()

    suspend fun addXpToCurrentUser(xp: Int) = userDao.addXp(1, xp)

    // ─── Lessons ───────────────────────────────────────────────────────────
    fun getAllLessons(): Flow<List<Lesson>> = lessonDao.getAllLessons()

    fun getLessonById(id: Int): Flow<Lesson?> = lessonDao.getLessonById(id)

    fun getLessonsByCategory(category: String): Flow<List<Lesson>> =
        lessonDao.getLessonsByCategory(category)

    // ─── Quizzes ───────────────────────────────────────────────────────────
    fun getQuizForLesson(lessonId: Int): Flow<Quiz?> = quizDao.getQuizForLesson(lessonId)

    fun getQuizById(quizId: Int): Flow<Quiz?> = quizDao.getQuizById(quizId)

    // ─── Questions ─────────────────────────────────────────────────────────
    fun getQuestionsForQuiz(quizId: Int): Flow<List<QuizQuestion>> =
        quizQuestionDao.getQuestionsForQuiz(quizId)

    // ─── Results ───────────────────────────────────────────────────────────
    fun getResultsForUser(userId: Int): Flow<List<QuizResult>> =
        quizResultDao.getResultsForUser(userId)

    fun getBestResultForQuiz(userId: Int, quizId: Int): Flow<QuizResult?> =
        quizResultDao.getBestResultForQuiz(userId, quizId)

    suspend fun saveQuizResult(userId: Int, quizId: Int, score: Int, xpReward: Int) {
        quizResultDao.insertResult(QuizResult(userId = userId, quizId = quizId, score = score))
        userDao.addXp(userId, xpReward)
    }
}
