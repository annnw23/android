package com.spaceexplorer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_questions")
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizId: Int,
    val questionText: String,
    val options: String,
    val correctOption: Int
)
