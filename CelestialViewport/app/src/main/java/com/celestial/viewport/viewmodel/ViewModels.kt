package com.celestial.viewport.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celestial.viewport.data.entity.*
import com.celestial.viewport.data.repository.CelestialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Dashboard ViewModel ──────────────────────────────────────────────────────
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: CelestialRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = repo.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val lessons: StateFlow<List<Lesson>> = repo.getAllLessons()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

// ─── Lesson Detail ViewModel ──────────────────────────────────────────────────
@HiltViewModel
class LessonDetailViewModel @Inject constructor(
    private val repo: CelestialRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lessonId: Int = checkNotNull(savedStateHandle["lessonId"])

    val lesson: StateFlow<Lesson?> = repo.getLessonById(lessonId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val quiz: StateFlow<Quiz?> = repo.getQuizForLesson(lessonId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

// ─── Quiz ViewModel ───────────────────────────────────────────────────────────
data class QuizUiState(
    val quiz: Quiz? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOption: Int? = null,
    val answeredCorrectly: Boolean? = null,
    val correctAnswers: Int = 0,
    val isFinished: Boolean = false,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repo: CelestialRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val quizId: Int = checkNotNull(savedStateHandle["quizId"])
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getQuizById(quizId).collect { quiz ->
                _uiState.update { it.copy(quiz = quiz) }
            }
        }
        viewModelScope.launch {
            repo.getQuestionsForQuiz(quizId).collect { questions ->
                _uiState.update { it.copy(questions = questions) }
            }
        }
    }

    fun selectOption(index: Int) {
        val state = _uiState.value
        if (state.answeredCorrectly != null) return // already answered
        val question = state.questions.getOrNull(state.currentQuestionIndex) ?: return
        val correct = index == question.correctOption
        _uiState.update {
            it.copy(
                selectedOption = index,
                answeredCorrectly = correct,
                correctAnswers = if (correct) it.correctAnswers + 1 else it.correctAnswers
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1
        if (nextIndex >= state.questions.size) {
            finishQuiz()
        } else {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedOption = null,
                    answeredCorrectly = null
                )
            }
        }
    }

    private fun finishQuiz() {
        _uiState.update { it.copy(isFinished = true) }
        val state = _uiState.value
        val score = if (state.questions.isEmpty()) 0
            else (state.correctAnswers * 100 / state.questions.size)
        val xpEarned = state.quiz?.points?.let { pts ->
            if (score >= (state.quiz.passingScore)) pts else pts / 4
        } ?: 0
        viewModelScope.launch {
            repo.saveQuizResult(userId = 1, quizId = quizId, score = score, xpReward = xpEarned)
        }
    }

    fun getFinalScore(): Int {
        val state = _uiState.value
        return if (state.questions.isEmpty()) 0
        else (state.correctAnswers * 100 / state.questions.size)
    }
}

// ─── Ranks ViewModel ──────────────────────────────────────────────────────────
@HiltViewModel
class RanksViewModel @Inject constructor(
    private val repo: CelestialRepository
) : ViewModel() {

    val rankedUsers: StateFlow<List<User>> = repo.getAllUsersRanked()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentUser: StateFlow<User?> = repo.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

// ─── Explore ViewModel ────────────────────────────────────────────────────────
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repo: CelestialRepository
) : ViewModel() {

    val lessons: StateFlow<List<Lesson>> = repo.getAllLessons()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredLessons: StateFlow<List<Lesson>> = combine(lessons, searchQuery) { all, q ->
        if (q.isBlank()) all
        else all.filter {
            it.title.contains(q, ignoreCase = true) ||
                    it.category.contains(q, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setQuery(q: String) { _searchQuery.value = q }
}
