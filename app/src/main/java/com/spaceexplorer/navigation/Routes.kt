package com.spaceexplorer.navigation

object Routes {
    const val WELCOME = "welcome"
    const val BROWSE = "browse"
    const val LEADERBOARD = "leaderboard"
    const val PROFILE = "profile"

    const val LESSON = "lesson/{lessonId}"
    const val QUIZ = "quiz/{quizId}"
    const val QUIZ_RESULT = "quiz_result/{score}/{quizId}"

    fun lesson(id: Int) = "lesson/$id"
    fun quiz(id: Int) = "quiz/$id"
    fun quizResult(score: Int, quizId: Int) = "quiz_result/$score/$quizId"
}
