package com.spaceexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.spaceexplorer.data.entity.Lesson
import com.spaceexplorer.navigation.Routes
import com.spaceexplorer.ui.theme.*
import com.spaceexplorer.ui.viewmodel.BrowseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    navController: NavController,
    viewModel: BrowseViewModel = viewModel()
) {
    val lessons by viewModel.lessons.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eksploruj", color = PrimaryText, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceColor)
            )
        },
        bottomBar = { SpaceBottomNav(navController) },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            if (lessons.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = CyanAccent)
                    }
                }
            } else {
                items(lessons) { lesson ->
                    LessonCard(
                        lesson = lesson,
                        onClick = { navController.navigate(Routes.lesson(lesson.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonCard(lesson: Lesson, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when (lesson.imageRes) {
                            "solar_system" -> androidx.compose.ui.graphics.Color(0xFF1A3050)
                            "black_hole" -> androidx.compose.ui.graphics.Color(0xFF0D0D1A)
                            else -> androidx.compose.ui.graphics.Color(0xFF1A2030)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (lesson.imageRes) {
                        "solar_system" -> "Układ Słoneczny"
                        "black_hole" -> "Czarna Dziura"
                        "moon" -> "Księżyc"
                        else -> lesson.title
                    },
                    color = SecondaryText,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryText,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = lesson.description.take(80) + "...",
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dowiedz się więcej", color = androidx.compose.ui.graphics.Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SpaceBottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = SurfaceColor) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Explore, contentDescription = "Przeglądaj") },
            label = { Text("Przeglądaj") },
            selected = currentRoute == Routes.BROWSE,
            onClick = {
                if (currentRoute != Routes.BROWSE) {
                    navController.navigate(Routes.BROWSE) {
                        popUpTo(Routes.BROWSE) { inclusive = true }
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyanAccent,
                selectedTextColor = CyanAccent,
                unselectedIconColor = SecondaryText,
                unselectedTextColor = SecondaryText,
                indicatorColor = CardBackground
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Ranking") },
            label = { Text("Ranking") },
            selected = currentRoute == Routes.LEADERBOARD,
            onClick = {
                if (currentRoute != Routes.LEADERBOARD) {
                    navController.navigate(Routes.LEADERBOARD)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyanAccent,
                selectedTextColor = CyanAccent,
                unselectedIconColor = SecondaryText,
                unselectedTextColor = SecondaryText,
                indicatorColor = CardBackground
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
            label = { Text("Profil") },
            selected = currentRoute == Routes.PROFILE,
            onClick = {
                if (currentRoute != Routes.PROFILE) {
                    navController.navigate(Routes.PROFILE)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyanAccent,
                selectedTextColor = CyanAccent,
                unselectedIconColor = SecondaryText,
                unselectedTextColor = SecondaryText,
                indicatorColor = CardBackground
            )
        )
    }
}
