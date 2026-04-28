package com.celestial.viewport.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// ─── User ───────────────────────────────────────────────────────────────────
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val xpTotal: Int = 0,
    val rank: String = "Cadet Explorer"
)

// ─── Lesson ─────────────────────────────────────────────────────────────────
@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val imageUrl: String = "",
    val category: String = "",
    val missionNumber: Int = 0
)

// ─── Quiz ────────────────────────────────────────────────────────────────────
@Entity(
    tableName = "quizzes",
    foreignKeys = [ForeignKey(
        entity = Lesson::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("lessonId")]
)
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonId: Int,
    val title: String,
    val passingScore: Int,
    val points: Int
)

// ─── Quiz Question ───────────────────────────────────────────────────────────
@Entity(
    tableName = "quiz_questions",
    foreignKeys = [ForeignKey(
        entity = Quiz::class,
        parentColumns = ["id"],
        childColumns = ["quizId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("quizId")]
)
@TypeConverters(OptionsConverter::class)
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizId: Int,
    val questionText: String,
    val options: List<String>,       // stored as JSON
    val correctOption: Int           // 0-based index
)

// ─── Quiz Result ─────────────────────────────────────────────────────────────
@Entity(
    tableName = "quiz_results",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Quiz::class,
            parentColumns = ["id"],
            childColumns = ["quizId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("quizId")]
)
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val quizId: Int,
    val score: Int
)

// ─── Type Converter for List<String> ──────────────────────────────────────
class OptionsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toJson(value: List<String>): String = gson.toJson(value)
}
