package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.model.LeaderboardItem
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LeaderboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    val topResults: StateFlow<List<LeaderboardItem>> = repository.getTopResults()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
