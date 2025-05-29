package com.example.bodybalance.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.util.DownloadCallback
import com.example.bodybalance.core.util.FileDownloaderError
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.ExistsPlaylistVideoByIdUseCase
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
    private val fileDownloaderImpl: FileDownloader,
    private val getVideoByCategoryUseCase: GetVideoByCategoryUseCase,
    private val addPlaylistVideoUseCase: AddPlaylistVideoUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase,
    private val existsPlaylistVideoByIdUseCase: ExistsPlaylistVideoByIdUseCase,
    private val getAllPlaylistVideosUseCase: GetAllPlaylistVideosUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerState>(VideoPlayerState.Loading)
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun handleEvent(event: VideoPlayerScreenUiEvent) {
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

    fun getPlaylistVideos(videoId: Int) {
        viewModelScope.launch {
            getAllPlaylistVideosUseCase().collect { playlistVideos ->
                val newState =
                    VideoPlayerState.Content(
                        currentVideo = playlistVideos.find { it.id == videoId }
                            ?: Video.emptyVideo(1),
                        videoList = playlistVideos,
                    )
                setButtonsState(newState)
            }
        }
    }

    fun getVideo(categoryId: Int) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            val result = getVideoByCategoryUseCase(categoryId)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> VideoPlayerState.Empty

                else -> result.getOrNull()?.let {
                    VideoPlayerState.Content(
                        currentVideo = it.map { video -> video }.first(),
                        videoList = it,
                    )
                } ?: VideoPlayerState.Empty
            }
            if (newState is VideoPlayerState.Content) {
                setButtonsState(newState)
            }
            _uiState.value = newState
        }
    }


    private fun selectVideo(video: Video) {
        _uiState.update {
            (it as VideoPlayerState.Content).copy(currentVideo = video)
        }
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is VideoPlayerState.Content) {
                setButtonsState(currentState)
            }
        }
    }

    private fun addToPlaylist(video: Video) {
        viewModelScope.launch {
            addPlaylistVideoUseCase(video = video)
            val currentState = _uiState.value
            if (currentState is VideoPlayerState.Content) {
                setButtonsState(currentState)
            }
        }
    }

    private fun removeFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video = video)
            val currentState = _uiState.value
            if (currentState is VideoPlayerState.Content) {
                setButtonsState(currentState)
            }
        }
    }

    private fun downloadVideo(url: String, fileName: String) {
        fileDownloaderImpl.downloadFile(url = url, fileName = fileName, object : DownloadCallback {
            override fun onSuccess(fileDownload: Boolean) {
                viewModelScope.launch {
                    val currentState = _uiState.value
                    if (currentState is VideoPlayerState.Content) {
                        setButtonsState(currentState)
                    }
                }
            }

            override fun onError(error: FileDownloaderError) {
                /*TODO("Not yet implemented")*/
            }
        })
    }

    private fun removeVideoFromCache(fileName: String) {
        fileDownloaderImpl.deleteFile(fileName = fileName).let {
            viewModelScope.launch {
                val currentState = _uiState.value
                if (currentState is VideoPlayerState.Content) {
                    setButtonsState(currentState)
                }
            }
        }
    }

    private suspend fun setButtonsState(state: VideoPlayerState.Content) {
        viewModelScope.launch {
            val test = fileDownloaderImpl.fileExists(state.currentVideo.id.toString())
            _uiState.value =
                state.copy(
                    videoInPlaylist = existsPlaylistVideoByIdUseCase(state.currentVideo.id),
                    videoInCache = test
                )
        }
    }
}