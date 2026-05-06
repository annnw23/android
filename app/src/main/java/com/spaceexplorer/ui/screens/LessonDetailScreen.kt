package com.spaceexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.spaceexplorer.navigation.Routes
import com.spaceexplorer.ui.theme.*
import com.spaceexplorer.ui.viewmodel.LessonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    lessonId: Int,
    navController: NavController,
    viewModel: LessonViewModel = viewModel()
) {
    LaunchedEffect(lessonId) {
        viewModel.loadLesson(lessonId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.lesson?.title ?: "Lekcja",
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz", tint = CyanAccent)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceColor)
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = CyanAccent)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = when (uiState.lesson?.imageRes) {
                                "solar_system" -> listOf(Color(0xFF1A3A6A), Color(0xFF0A1A30))
                                "black_hole" -> listOf(Color(0xFF0D0010), Color(0xFF1A0030))
                                "moon" -> listOf(Color(0xFF202840), Color(0xFF0A0E1A))
                                else -> listOf(SurfaceColor, DarkBackground)
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.lesson?.title ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    color = PrimaryText.copy(alpha = 0.3f),
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = uiState.lesson?.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = CyanAccent,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = uiState.lesson?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PrimaryText,
                    lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.6
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        uiState.quiz?.let { quiz ->
                            navController.navigate(Routes.quiz(quiz.id))
                        }
                    },
                    enabled = uiState.quiz != null,
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeAccent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        "Rozpocznij Quiz",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
