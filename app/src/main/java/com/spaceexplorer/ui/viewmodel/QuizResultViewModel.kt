package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.QuizResult
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizResultViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    fun saveResult(username: String, quizId: Int, score: Int) {
        viewModelScope.launch {
            val userId = repository.findOrCreateUser(username.trim())
            repository.saveQuizResult(
                QuizResult(userId = userId, quizId = quizId, score = score)
            )
            _saved.value = true
        }
    }
}
