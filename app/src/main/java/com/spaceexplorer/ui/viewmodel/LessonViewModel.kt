package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.Lesson
import com.spaceexplorer.data.entity.Quiz
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LessonUiState(
    val lesson: Lesson? = null,
    val quiz: Quiz? = null,
    val isLoading: Boolean = true
)

class LessonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun loadLesson(id: Int) {
        viewModelScope.launch {
            val lesson = repository.getLessonById(id)
            val quiz = repository.getQuizByLessonId(id)
            _uiState.update { it.copy(lesson = lesson, quiz = quiz, isLoading = false) }
        }
    }
}
