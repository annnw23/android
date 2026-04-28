package com.celestial.viewport.ui

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Dashboard : Screen("dashboard")
    object Explore : Screen("explore")
    object Missions : Screen("missions")
    object Ranks : Screen("ranks")
    object LessonDetail : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: Int) = "lesson/$lessonId"
    }
    object Quiz : Screen("quiz/{quizId}") {
        fun createRoute(quizId: Int) = "quiz/$quizId"
    }
    object QuizResult : Screen("quiz_result/{quizId}/{score}/{total}") {
        fun createRoute(quizId: Int, score: Int, total: Int) = "quiz_result/$quizId/$score/$total"
    }
}
