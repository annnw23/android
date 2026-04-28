package com.celestial.viewport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.celestial.viewport.ui.Screen
import com.celestial.viewport.ui.components.CelestialBottomNav
import com.celestial.viewport.ui.components.CelestialTopBar
import com.celestial.viewport.ui.screens.*
import com.celestial.viewport.ui.theme.CelestialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CelestialTheme {
                CelestialApp()
            }
        }
    }
}

@Composable
fun CelestialApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    // Screens where bottom nav + top bar are shown
    val mainScreenRoutes = setOf(
        Screen.Dashboard.route,
        Screen.Explore.route,
        Screen.Missions.route,
        Screen.Ranks.route,
    )
    val showBottomBar = currentRoute in mainScreenRoutes
    val showTopBar = currentRoute in mainScreenRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                CelestialTopBar(
                    title = when (currentRoute) {
                        Screen.Ranks.route -> "Celestial Viewport"
                        Screen.Explore.route -> "Explore"
                        Screen.Missions.route -> "Missions"
                        else -> "Celestial Viewport"
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                CelestialBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Onboarding
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onStartMission = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            // Dashboard
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    onLessonClick = { id ->
                        navController.navigate(Screen.LessonDetail.createRoute(id))
                    }
                )
            }

            // Explore
            composable(Screen.Explore.route) {
                ExploreScreen(
                    onLessonClick = { id ->
                        navController.navigate(Screen.LessonDetail.createRoute(id))
                    }
                )
            }

            // Missions
            composable(Screen.Missions.route) {
                MissionsScreen(
                    onLessonClick = { id ->
                        navController.navigate(Screen.LessonDetail.createRoute(id))
                    }
                )
            }

            // Ranks
            composable(Screen.Ranks.route) {
                RanksScreen()
            }

            // Lesson Detail
            composable(
                route = Screen.LessonDetail.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
            ) {
                LessonDetailScreen(
                    onBack = { navController.popBackStack() },
                    onTakeQuiz = { quizId ->
                        navController.navigate(Screen.Quiz.createRoute(quizId))
                    }
                )
            }

            // Quiz
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("quizId") { type = NavType.IntType })
            ) {
                QuizScreen(
                    onBack = { navController.popBackStack() },
                    onFinished = { score, total ->
                        val quizId = it.arguments?.getInt("quizId") ?: 0
                        navController.navigate(Screen.QuizResult.createRoute(quizId, score, total)) {
                            popUpTo(Screen.Quiz.route) { inclusive = true }
                        }
                    }
                )
            }

            // Quiz Result
            composable(
                route = Screen.QuizResult.route,
                arguments = listOf(
                    navArgument("quizId") { type = NavType.IntType },
                    navArgument("score") { type = NavType.IntType },
                    navArgument("total") { type = NavType.IntType },
                )
            ) { backStack ->
                val quizId = backStack.arguments?.getInt("quizId") ?: 0
                val score = backStack.arguments?.getInt("score") ?: 0
                val total = backStack.arguments?.getInt("total") ?: 0
                QuizResultScreen(
                    score = score,
                    total = total,
                    onRetry = {
                        navController.navigate(Screen.Quiz.createRoute(quizId)) {
                            popUpTo(Screen.QuizResult.route) { inclusive = true }
                        }
                    },
                    onDone = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                                saveState = true
                            }
                        }
                    }
                )
            }
        }
    }
}
