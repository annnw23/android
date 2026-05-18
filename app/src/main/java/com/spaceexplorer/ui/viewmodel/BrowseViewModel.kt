package com.spaceexplorer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spaceexplorer.data.database.AppDatabase
import com.spaceexplorer.data.entity.Lesson
import com.spaceexplorer.data.repository.SpaceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BrowseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceRepository(AppDatabase.getInstance(application))

    val lessons: StateFlow<List<Lesson>> = repository.getAllLessons()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    // Flow z DAO ma ORDER BY isCompleted ASC → lista automatycznie
    // przesuwa ukończone lekcje na dół gdy toggleCompleted() zmieni wartość w bazie

    fun toggleCompleted(lesson: Lesson) {
        // TO JEST KLUCZOWA FUNKCJA — zapis do bazy przez ViewModel
        viewModelScope.launch {
            // viewModelScope → korutyna powiązana z życiem ViewModelu
            // launch → uruchamiamy operację asynchronicznie (nie blokujemy UI)
            repository.toggleLessonCompleted(
                lessonId = lesson.id,
                isCompleted = !lesson.isCompleted
                // !lesson.isCompleted → odwracamy aktualny stan
                // false → true (oznacz jako ukończona)
                // true → false (cofnij oznaczenie)
            )
            // po wykonaniu UPDATE, Flow w DAO emituje zaktualizowaną listę
            // StateFlow w ViewModelu dostaje nowe dane
            // BrowseScreen automatycznie się przerysowuje z nową kolejnością
            // NIE MUSIMY ręcznie odświeżać listy — reaktywność Flow robi to za nas
        }
    }
}
