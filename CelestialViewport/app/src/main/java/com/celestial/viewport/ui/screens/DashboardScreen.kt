package com.celestial.viewport.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.celestial.viewport.data.entity.Lesson
import com.celestial.viewport.ui.components.GlassCard
import com.celestial.viewport.ui.components.StatChip
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onLessonClick: (Int) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val user by viewModel.currentUser.collectAsState()
    val lessons by viewModel.lessons.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero / greeting card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Primary.copy(alpha = 0.1f),
                                    Background
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .statusBarsPadding()
                        .padding(top = 56.dp),
                ) {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = user?.username ?: "Explorer",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Primary
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatChip("XP Total", "${(user?.xpTotal ?: 0) / 1000}k", Primary)
                        StatChip("Rank", user?.rank?.split(" ")?.first() ?: "Cadet", Secondary)
                    }
                }
            }
        }

        // Section header
        item {
            Text(
                text = "ACTIVE MISSIONS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }

        // Featured lesson
        lessons.firstOrNull()?.let { lesson ->
            item {
                FeaturedLessonCard(
                    lesson = lesson,
                    onClick = { onLessonClick(lesson.id) },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(24.dp))
            }
        }

        item {
            Text(
                text = "ALL SECTORS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        // Lesson list
        items(lessons) { lesson ->
            LessonListItem(
                lesson = lesson,
                onClick = { onLessonClick(lesson.id) },
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FeaturedLessonCard(lesson: Lesson, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = lesson.imageUrl,
            contentDescription = lesson.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(Background.copy(alpha = 0f), Background.copy(alpha = 0.9f)))
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "MISSION #${lesson.missionNumber.toString().padStart(3, '0')}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Primary,
                    letterSpacing = 2.sp
                )
            )
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
        }
    }
}

@Composable
private fun LessonListItem(lesson: Lesson, onClick: () -> Unit, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = lesson.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = lesson.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = OnSurfaceVariant
            )
        }
    }
}
