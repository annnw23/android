package com.celestial.viewport.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.celestial.viewport.data.entity.Lesson
import com.celestial.viewport.ui.components.GlassCard
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.DashboardViewModel

@Composable
fun MissionsScreen(
    onLessonClick: (Int) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val lessons by viewModel.lessons.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Spacer(Modifier.statusBarsPadding().height(72.dp))
            Text(
                "YOUR MISSIONS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        itemsIndexed(lessons) { index, lesson ->
            MissionRow(
                lesson = lesson,
                isCompleted = index < 2, // first 2 "completed" for demo
                onClick = { onLessonClick(lesson.id) },
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun MissionRow(
    lesson: Lesson,
    isCompleted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isCompleted) Primary.copy(alpha = 0.15f) else SurfaceContainerHighest
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (isCompleted) Primary else OnSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = "MISSION #${lesson.missionNumber.toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isCompleted) Primary.copy(0.7f) else OnSurfaceVariant,
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) OnSurface.copy(0.7f) else OnSurface
                    )
                )
                Text(
                    text = lesson.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
            if (isCompleted) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        "DONE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Primary,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            } else {
                Icon(Icons.Default.ChevronRight, null, tint = OnSurfaceVariant)
            }
        }
    }
}
