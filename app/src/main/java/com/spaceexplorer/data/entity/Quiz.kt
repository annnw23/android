package com.spaceexplorer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonId: Int,
    val title: String,
    val passingScore: Int,
    val points: Int
)
