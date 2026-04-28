package com.celestial.viewport.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.celestial.viewport.ui.components.CosmicButton
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    onBack: () -> Unit,
    onFinished: (Int, Int) -> Unit, // score, total
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isFinished) {
        if (state.isFinished) {
            onFinished(state.correctAnswers, state.questions.size)
        }
    }

    val currentQuestion = state.questions.getOrNull(state.currentQuestionIndex)
    val progress = if (state.questions.isEmpty()) 0f
        else (state.currentQuestionIndex + 1).toFloat() / state.questions.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = OnSurfaceVariant)
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(
                    text = state.quiz?.title ?: "Quiz",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .height(4.dp),
                    color = Primary,
                    trackColor = SurfaceContainerHighest,
                )
            }
            Text(
                text = "${state.currentQuestionIndex + 1} / ${state.questions.size}",
                style = MaterialTheme.typography.labelMedium.copy(color = OnSurfaceVariant)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Question card
        AnimatedContent(
            targetState = state.currentQuestionIndex,
            label = "question",
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            }
        ) { _ ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                currentQuestion?.let { q ->
                    // Question text
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(GlassPanel)
                            .padding(24.dp)
                    ) {
                        Text(
                            text = q.questionText,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 30.sp
                            ),
                            color = OnSurface
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // Answer options
                    q.options.forEachIndexed { index, option ->
                        AnswerOption(
                            text = option,
                            index = index,
                            isSelected = state.selectedOption == index,
                            isCorrect = if (state.answeredCorrectly != null) index == q.correctOption else null,
                            isWrong = state.selectedOption == index && state.answeredCorrectly == false,
                            onClick = { viewModel.selectOption(index) }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // Result feedback + Next button
        AnimatedVisibility(visible = state.answeredCorrectly != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .navigationBarsPadding()
            ) {
                val isCorrect = state.answeredCorrectly == true
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isCorrect) Primary.copy(alpha = 0.1f) else Error.copy(alpha = 0.1f)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        tint = if (isCorrect) Primary else Error,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = if (isCorrect) "Correct! Well done, Explorer." else "Not quite. Keep learning!",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = if (isCorrect) Primary else Error
                    )
                }
                Spacer(Modifier.height(12.dp))
                CosmicButton(
                    text = if (state.currentQuestionIndex + 1 >= state.questions.size) "See Results" else "Confirm & Next",
                    onClick = { viewModel.nextQuestion() },
                    leadingIcon = if (state.currentQuestionIndex + 1 >= state.questions.size)
                        Icons.Default.EmojiEvents else Icons.Default.ArrowForward
                )
            }
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    index: Int,
    isSelected: Boolean,
    isCorrect: Boolean?,     // null = not answered yet
    isWrong: Boolean,
    onClick: () -> Unit
) {
    val containerColor = when {
        isCorrect == true -> Primary.copy(alpha = 0.15f)
        isWrong -> Error.copy(alpha = 0.15f)
        isSelected -> Primary.copy(alpha = 0.08f)
        else -> GlassPanel
    }
    val borderColor = when {
        isCorrect == true -> Primary
        isWrong -> Error
        isSelected -> Primary.copy(alpha = 0.5f)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(enabled = isCorrect == null, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Option letter
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    when {
                        isCorrect == true -> Primary
                        isWrong -> Error
                        else -> SurfaceContainerHighest
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ('A' + index).toString(),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isCorrect == true -> OnPrimaryContainer
                        isWrong -> OnError
                        else -> OnSurfaceVariant
                    }
                )
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                isCorrect == true -> Primary
                isWrong -> Error
                else -> OnSurface
            }
        )
    }
}
