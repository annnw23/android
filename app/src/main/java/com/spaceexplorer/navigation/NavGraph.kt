package com.spaceexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.spaceexplorer.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(navController = navController)
        }

        composable(Routes.BROWSE) {
            BrowseScreen(navController = navController)
        }

        composable(
            route = Routes.LESSON,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: return@composable
            LessonDetailScreen(lessonId = lessonId, navController = navController)
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(navArgument("quizId") { type = NavType.IntType })
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getInt("quizId") ?: return@composable
            QuizScreen(quizId = quizId, navController = navController)
        }

        composable(
            route = Routes.QUIZ_RESULT,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("quizId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val quizId = backStackEntry.arguments?.getInt("quizId") ?: return@composable
            QuizResultScreen(score = score, quizId = quizId, navController = navController)
        }

        composable(Routes.LEADERBOARD) {
            LeaderboardScreen(navController = navController)
        }

        composable(Routes.PROFILE) {
            ProfileScreen(navController = navController)
        }
    }
}
