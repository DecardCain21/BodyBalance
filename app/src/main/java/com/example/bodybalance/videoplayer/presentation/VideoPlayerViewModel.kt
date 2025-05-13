package com.example.bodybalance.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.util.FileDownloader
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState
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
    private val fileDownloader: FileDownloader,
    private val getVideoByCategoryUseCase: GetVideoByCategoryUseCase,
    private val addPlaylistVideoUseCase: AddPlaylistVideoUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerState>(VideoPlayerState.Loading)
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun handleEvent(event: VideoPlayerScreenUiEvent) {
        //Проверить есть ли видео в кэше и плейлисте
        when (event) {
            is VideoPlayerScreenUiEvent.DownloadVideo -> {
                downloadVideo(url = event.url, fileName = event.fileName)
            }

            is VideoPlayerScreenUiEvent.RemoveVideoFromCache -> {
                removeVideoFromCache(event.fileName)
            }

            is VideoPlayerScreenUiEvent.ChoiceVideo -> {
                selectVideo(event.video)
            }

            is VideoPlayerScreenUiEvent.AddToPlaylist -> {
                addToPlaylist(event.video)
            }

            is VideoPlayerScreenUiEvent.RemoveFromPlaylist -> {
                removeFromPlaylist(event.video)
            }
        }
    }

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


    private fun selectVideo(video: Video) {
        _uiState.update {
            (it as VideoPlayerState.Content).copy(currentVideo = video)
        }
    }

    private fun addToPlaylist(video: Video) {
        viewModelScope.launch {
            addPlaylistVideoUseCase(video = video)
        }
    }

    private fun removeFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video = video)
        }
    }

    private fun downloadVideo(url: String, fileName: String) {
        fileDownloader.downloadFile(url = url, fileName = fileName)
    }

    private fun removeVideoFromCache(fileName: String) {

    }
}