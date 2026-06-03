package com.spaceexplorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GalleryUiState(
    val imageUrls: List<String> = emptyList(),
    val isPlaying: Boolean = false
)

class GalleryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    init {
        loadImages()
    }

    private fun loadImages() {
        val nasaImages = listOf(
            "https://images-assets.nasa.gov/image/PIA12235/PIA12235~thumb.jpg",
            "https://images-assets.nasa.gov/image/iss040e079433/iss040e079433~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA08653/PIA08653~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA15416/PIA15416~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA14451/PIA14451~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA10125/PIA10125~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA08665/PIA08665~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA12110/PIA12110~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA03652/PIA03652~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA00122/PIA00122~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA01466/PIA01466~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA01977/PIA01977~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA02210/PIA02210~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA03153/PIA03153~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA03654/PIA03654~thumb.jpg",
            "https://images-assets.nasa.gov/image/PIA04921/PIA04921~thumb.jpg"
        )
        _uiState.value = GalleryUiState(imageUrls = nasaImages)
    }

    fun togglePlayback(isPlaying: Boolean) {
        _uiState.value = _uiState.value.copy(isPlaying = isPlaying)
    }
}
