package com.spaceexplorer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val quizId: Int,
    val score: Int,
    val timestamp: Long = System.currentTimeMillis()
)
