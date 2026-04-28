package com.celestial.viewport.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.celestial.viewport.data.entity.Lesson
import com.celestial.viewport.ui.components.GlassCard
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.ExploreViewModel

@Composable
fun ExploreScreen(
    onLessonClick: (Int) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val query by viewModel.searchQuery.collectAsState()
    val lessons by viewModel.filteredLessons.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Spacer(Modifier.statusBarsPadding().height(72.dp))
            // Search bar
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::setQuery,
                placeholder = {
                    Text(
                        "Search sectors…",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, null, tint = OnSurfaceVariant)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setQuery("") }) {
                            Icon(Icons.Default.Close, null, tint = OnSurfaceVariant)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = OutlineVariant,
                    focusedContainerColor = GlassPanel,
                    unfocusedContainerColor = GlassPanel,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                text = "${lessons.size} SECTORS FOUND",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        items(lessons) { lesson ->
            ExploreCard(
                lesson = lesson,
                onClick = { onLessonClick(lesson.id) },
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 6.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ExploreCard(lesson: Lesson, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(16.dp))
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
                    androidx.compose.ui.graphics.Brush.horizontalGradient(
                        listOf(Background.copy(alpha = 0.9f), Background.copy(alpha = 0.3f))
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp)
        ) {
            Text(
                text = lesson.category.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Secondary,
                    letterSpacing = 1.5.sp
                )
            )
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
            Text(
                text = "Mission #${lesson.missionNumber.toString().padStart(3, '0')}",
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant
            )
        }
    }
}
