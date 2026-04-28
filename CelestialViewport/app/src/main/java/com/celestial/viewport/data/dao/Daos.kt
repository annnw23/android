package com.celestial.viewport.data.dao

import androidx.room.*
import com.celestial.viewport.data.entity.*
import kotlinx.coroutines.flow.Flow

// ─── User DAO ─────────────────────────────────────────────────────────────────
@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): Flow<User?>

    @Query("SELECT * FROM user ORDER BY xpTotal DESC")
    fun getAllUsersRanked(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE user SET xpTotal = xpTotal + :xp WHERE id = :userId")
    suspend fun addXp(userId: Int, xp: Int)
}

// ─── Lesson DAO ──────────────────────────────────────────────────────────────
@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY missionNumber ASC")
    fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE id = :id")
    fun getLessonById(id: Int): Flow<Lesson?>

    @Query("SELECT * FROM lessons WHERE category = :category")
    fun getLessonsByCategory(category: String): Flow<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<Lesson>)
}

// ─── Quiz DAO ────────────────────────────────────────────────────────────────
@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId")
    fun getQuizForLesson(lessonId: Int): Flow<Quiz?>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizById(quizId: Int): Flow<Quiz?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<Quiz>)
}

// ─── Quiz Question DAO ───────────────────────────────────────────────────────
@Dao
interface QuizQuestionDao {
    @Query("SELECT * FROM quiz_questions WHERE quizId = :quizId ORDER BY id ASC")
    fun getQuestionsForQuiz(quizId: Int): Flow<List<QuizQuestion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuizQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestion>)
}

// ─── Quiz Result DAO ─────────────────────────────────────────────────────────
@Dao
interface QuizResultDao {
    @Query("SELECT * FROM quiz_results WHERE userId = :userId ORDER BY id DESC")
    fun getResultsForUser(userId: Int): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results WHERE userId = :userId AND quizId = :quizId LIMIT 1")
    fun getBestResultForQuiz(userId: Int, quizId: Int): Flow<QuizResult?>

    @Query("""
        SELECT SUM(score) FROM quiz_results 
        WHERE userId = :userId
    """)
    fun getTotalScoreForUser(userId: Int): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: QuizResult): Long
}
