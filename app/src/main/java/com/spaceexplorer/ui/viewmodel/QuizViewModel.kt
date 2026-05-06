package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.Quiz
import com.spaceexplorer.data.entity.QuizQuestion
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val quiz: Quiz? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOption: Int? = null,
    val answers: List<Int?> = emptyList(),
    val isFinished: Boolean = false,
    val score: Int = 0,
    val isLoading: Boolean = true
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun loadQuiz(quizId: Int) {
        viewModelScope.launch {
            val quiz = repository.getQuizById(quizId)
            val questions = repository.getQuestionsByQuizId(quizId)
            _uiState.update {
                it.copy(
                    quiz = quiz,
                    questions = questions,
                    answers = List(questions.size) { null },
                    isLoading = false
                )
            }
        }
    }

    fun selectOption(option: Int) {
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val updatedAnswers = state.answers.toMutableList().also {
            it[state.currentIndex] = state.selectedOption
        }

        if (state.currentIndex < state.questions.size - 1) {
            _uiState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedOption = null,
                    answers = updatedAnswers
                )
            }
        } else {
            val score = updatedAnswers.indices.count { i ->
                updatedAnswers[i] == state.questions[i].correctOption
            }
            _uiState.update {
                it.copy(
                    answers = updatedAnswers,
                    isFinished = true,
                    score = score
                )
            }
        }
    }
}
