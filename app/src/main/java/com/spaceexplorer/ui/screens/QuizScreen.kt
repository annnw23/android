package com.spaceexplorer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.spaceexplorer.navigation.Routes
import com.spaceexplorer.ui.theme.*
import com.spaceexplorer.ui.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quizId: Int,
    navController: NavController,
    viewModel: QuizViewModel = viewModel()
) {
    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished && uiState.quiz != null) {
            navController.navigate(Routes.quizResult(uiState.score, uiState.quiz!!.id)) {
                popUpTo(Routes.quiz(quizId)) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.quiz?.title ?: "Quiz",
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
        if (uiState.isLoading || uiState.questions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = CyanAccent)
            }
            return@Scaffold
        }

        val question = uiState.questions[uiState.currentIndex]
        val options = remember(question.options) {
            Gson().fromJson(question.options, Array<String>::class.java).toList()
        }
        val total = uiState.questions.size
        val progress = (uiState.currentIndex + 1).toFloat() / total

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Pytanie ${uiState.currentIndex + 1} z $total",
                style = MaterialTheme.typography.labelLarge,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = CyanAccent,
                trackColor = SurfaceColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = question.questionText,
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryText,
                    modifier = Modifier.padding(20.dp),
                    lineHeight = MaterialTheme.typography.titleLarge.fontSize * 1.4
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            options.forEachIndexed { index, option ->
                val isSelected = uiState.selectedOption == index
                OutlinedButton(
                    onClick = { viewModel.selectOption(index) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) CyanAccent else SecondaryText.copy(alpha = 0.5f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) CyanAccent.copy(alpha = 0.15f) else Color.Transparent
                    )
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) CyanAccent else PrimaryText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.nextQuestion() },
                enabled = uiState.selectedOption != null,
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = if (uiState.currentIndex < uiState.questions.size - 1) "Dalej" else "Zakończ",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
