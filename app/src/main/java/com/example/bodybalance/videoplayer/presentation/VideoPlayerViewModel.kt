package com.example.bodybalance.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoByCategoryUseCase: GetVideoByCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerState>(VideoPlayerState.Loading)
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun getVideo(category: String) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            val result = getVideoByCategoryUseCase(category)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> VideoPlayerState.Empty

                else -> result.getOrNull()?.let {
                    VideoPlayerState.Content(
                        currentVideo = it.videoItems.map { video -> video }.first(),
                        videoList = it.videoItems
                    )
                } ?: VideoPlayerState.Empty
            }
            _uiState.value = newState
        }
    }


    fun selectVideo(video: Video) {
        _uiState.update {
            (it as VideoPlayerState.Content).copy(currentVideo = video)
        }
    }
}