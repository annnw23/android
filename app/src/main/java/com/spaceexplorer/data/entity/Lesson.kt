package com.spaceexplorer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val imageRes: String,
    val isCompleted: Boolean = false
    // false = lekcja nieukończona (domyślnie)
    // Room przechowuje Boolean jako INTEGER: 0 = false, 1 = true
)
