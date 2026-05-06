package com.spaceexplorer.data.model

data class LeaderboardItem(
    val score: Int,
    val username: String,
    val quizTitle: String,
    val timestamp: Long
)
