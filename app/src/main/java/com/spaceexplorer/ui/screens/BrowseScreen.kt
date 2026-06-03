package com.spaceexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
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
                items(
                    items = lessons,
                    key = { it.id }
                ) { lesson ->
                    LessonCard(
                        lesson = lesson,
                        onClick = { navController.navigate(Routes.lesson(lesson.id)) },
                        onToggleCompleted = { viewModel.toggleCompleted(lesson) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonCard(
    lesson: Lesson,
    onClick: () -> Unit,
    onToggleCompleted: () -> Unit
) {
    val completedGreen = Color(0xFF4CAF50)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (lesson.isCompleted) 0.75f else 1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (lesson.isCompleted) Color(0xFF1B3A2A) else CardBackground
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Obrazek z URL (NASA API/Archive)
            AsyncImage(
                model = lesson.imageRes,
                contentDescription = lesson.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // tytuł
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryText,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // opis
            Text(
                text = if (lesson.description.length > 80) lesson.description.take(80) + "..." else lesson.description,
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // CHECKBOX
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = lesson.isCompleted,
                    onCheckedChange = { onToggleCompleted() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = completedGreen,
                        uncheckedColor = SecondaryText,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = if (lesson.isCompleted) "Ukończona" else "Oznacz jako ukończona",
                    color = if (lesson.isCompleted) completedGreen else SecondaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (lesson.isCompleted) FontWeight.Bold else FontWeight.Normal
                )
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
            icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = "Galeria") },
            label = { Text("Galeria") },
            selected = currentRoute == Routes.GALLERY,
            onClick = {
                if (currentRoute != Routes.GALLERY) {
                    navController.navigate(Routes.GALLERY)
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
