package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.Lesson
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class BrowseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    val lessons: StateFlow<List<Lesson>> = repository.getAllLessons()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
