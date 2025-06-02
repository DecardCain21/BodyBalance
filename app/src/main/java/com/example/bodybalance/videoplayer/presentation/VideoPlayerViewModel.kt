package com.example.bodybalance.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.util.DownloadCallback
import com.example.bodybalance.core.util.FileDownloaderError
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.SnackbarEventParams
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.ExistsPlaylistVideoByIdUseCase
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
internal class VideoPlayerViewModel @Inject constructor(
    private val fileDownloaderImpl: FileDownloader,
    private val getVideoByCategoryUseCase: GetVideoByCategoryUseCase,
    private val addPlaylistVideoUseCase: AddPlaylistVideoUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase,
    private val existsPlaylistVideoByIdUseCase: ExistsPlaylistVideoByIdUseCase,
    private val getAllPlaylistVideosUseCase: GetAllPlaylistVideosUseCase,
    private val settingsToolsUseCase: SettingsToolsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoPlayerState.emptyState())
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackbarEventParams>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

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
                    VideoPlayerState(
                        videoState = VideoPlayerState.VideoState.Content(
                            playlistVideos.find { it.id == videoId }
                                ?: Video.emptyVideo(1)
                        ),
                        videoListState = VideoPlayerState.VideoListState.Content(playlistVideos),
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
                is NetworkError.NoInternet -> VideoPlayerState.emptyState()

                else -> result.getOrNull()?.let {
                    VideoPlayerState(
                        videoState = VideoPlayerState.VideoState.Content(getVideoFromCache(it.first())),
                        videoListState = VideoPlayerState.VideoListState.Content(it),
                    )
                }
            }
            if (newState is VideoPlayerState) {
                setButtonsState(newState)
            }
            if (newState != null) {
                _uiState.value = newState
            }
        }
    }

    private fun selectVideo(video: Video) {
        _uiState.update {
            it.copy(videoState = VideoPlayerState.VideoState.Content(getVideoFromCache(video)))
        }
        viewModelScope.launch {
            val currentState = _uiState.value
            setButtonsState(currentState)
        }
    }

    private fun getVideoFromCache(video: Video): Video {
        val url = fileDownloaderImpl.getFilePathIfExists(video.id.toString())
        return video.copy(url = url ?: video.url)
    }

    private fun addToPlaylist(video: Video) {
        viewModelScope.launch {
            addPlaylistVideoUseCase(video = video)
            val currentState = _uiState.value
            setButtonsState(currentState)

        }
    }

    private fun removeFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video = video)
            val currentState = _uiState.value
            setButtonsState(currentState)
        }
    }

    private fun downloadVideo(url: String, fileName: String) {
        viewModelScope.launch {
            _snackBarEvent.emit(
                SnackbarEventParams(message = VIDEO_IS_BEING_DOWNLOADED)
            )
        }
        fileDownloaderImpl.downloadFile(url = url, fileName = fileName, object : DownloadCallback {
            override fun onSuccess(fileDownload: Boolean) {
                viewModelScope.launch {
                    _snackBarEvent.emit(
                        SnackbarEventParams(message = VIDEO_DOWNLOADED)
                    )
                    val currentState = _uiState.value
                    setButtonsState(currentState)
                }
            }

            override fun onError(error: FileDownloaderError) {
                viewModelScope.launch {
                    when (error) {
                        FileDownloaderError.WIFI_ERROR -> {
                            _snackBarEvent.emit(
                                SnackbarEventParams(
                                    message = error.error,
                                    actionLabel = ACTION_LABEL_UNPLUG,
                                    onAction = { settingsToolsUseCase.setWifiFlag(false) }
                                )
                            )
                        }

                        else -> _snackBarEvent.emit(SnackbarEventParams(message = error.error))
                    }
                }
            }
        })
    }

    private fun removeVideoFromCache(fileName: String) {
        fileDownloaderImpl.deleteFile(fileName = fileName).let {
            viewModelScope.launch {
                val currentState = _uiState.value
                setButtonsState(currentState)

            }
        }
    }

    private suspend fun setButtonsState(state: VideoPlayerState) {
        viewModelScope.launch {
            if (state.videoState is VideoPlayerState.VideoState.Content) {
                _uiState.value =
                    state.copy(
                        videoInPlaylist = existsPlaylistVideoByIdUseCase(state.videoState.video.id),
                        videoInCache = fileDownloaderImpl.fileExists(state.videoState.video.id.toString())
                    )
            } else {
                _uiState.value =
                    state.copy(
                        videoInPlaylist = false,
                        videoInCache = false
                    )
            }
        }
    }

    companion object {
        private const val VIDEO_IS_BEING_DOWNLOADED = "Видео скачивается"
        private const val VIDEO_DOWNLOADED = "Видео скачено"
        private const val ACTION_LABEL_UNPLUG = "Отключить"
    }
}