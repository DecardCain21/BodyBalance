package com.example.bodybalance.videoplayer.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.DeleteSavedVideoUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.util.DownloadCallback
import com.example.bodybalance.core.util.FileDownloaderError
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.SnackbarEventParams
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.core.util.getConnected
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.ExistsPlaylistVideoByIdUseCase
import com.example.bodybalance.videoplayer.domain.usecase.GetAllPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.GetAllSavedVideoUseCase
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState.DownloadButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val fileDownloader: FileDownloader,
    private val getVideoByCategoryUseCase: GetVideoByCategoryUseCase,
    private val addPlaylistVideoUseCase: AddPlaylistVideoUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase,
    private val existsPlaylistVideoByIdUseCase: ExistsPlaylistVideoByIdUseCase,
    private val getAllPlaylistVideoUseCase: GetAllPlaylistVideoUseCase,
    private val settingsToolsUseCase: SettingsToolsUseCase,
    private val getAllSavedVideoUseCase: GetAllSavedVideoUseCase,
    private val deleteSavedVideoUseCase: DeleteSavedVideoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoPlayerState.emptyState())
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackbarEventParams>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    private var isInitialized = false
    private lateinit var deleteJob: Job

    fun handleEvent(event: VideoPlayerScreenUiEvent) {
        when (event) {
            is VideoPlayerScreenUiEvent.DownloadVideo -> {
                downloadVideo(video = event.video)
            }

            is VideoPlayerScreenUiEvent.RemoveVideoFromCache -> {
                removeVideoFromCache(event.video)
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

            is VideoPlayerScreenUiEvent.CanselDownloadVideo -> {
                canselDownloadVideo(event.videoId)
            }

            is VideoPlayerScreenUiEvent.EmptyEvent -> {
                emptyEvent()
            }

            is VideoPlayerScreenUiEvent.NothingToPlayNoInternet -> {
                nothingToPlayNoInternet()
            }
        }
    }

    fun getAllPlaylistVideo(videoId: Int) {
        viewModelScope.launch {
            setDownloadButton(videoId)
            val inPlaylist = existsPlaylistVideoByIdUseCase(videoId)
            val listVideo = getAllPlaylistVideoUseCase()
            val currentVideo = getVideoFromCache(listVideo.find { it.id == videoId }
                ?: Video.emptyVideo(1))
            _uiState.update { state ->
                state.copy(
                    videoState = VideoPlayerState.VideoState.Content(currentVideo),
                    videoListState = VideoPlayerState.VideoListState.Content(listVideo),
                    videoInPlaylist = inPlaylist,
                )
            }
        }
    }

    fun getAllDownloadedVideo(videoId: Int) {
        viewModelScope.launch {
            val videos = getAllSavedVideoUseCase()
            val inPlaylist = existsPlaylistVideoByIdUseCase(videoId)
            setDownloadButton(videoId)
            _uiState.update { state ->
                state.copy(
                    videoState = VideoPlayerState.VideoState.Content(
                        videos.find { it.id == videoId }
                            ?: Video.emptyVideo(1)
                    ),
                    videoListState = VideoPlayerState.VideoListState.Content(videos),
                    videoInPlaylist = inPlaylist,
                )
            }
        }
    }

    fun getVideo(categoryId: Int) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            val result = getVideoByCategoryUseCase(categoryId)
            when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData -> {
                    _uiState.update {
                        it.copy(
                            videoState = VideoPlayerState.VideoState.Empty,
                            videoListState = VideoPlayerState.VideoListState.Empty
                        )
                    }
                }

                is NetworkError.NoInternet -> _uiState.value = VideoPlayerState.emptyState()

                else -> result.getOrNull()?.let { videos ->
                    val video = getVideoFromCache(videos.first())

                    val inPlaylist = existsPlaylistVideoByIdUseCase(video.id)
                    setDownloadButton(video.id)
                    _uiState.update {
                        it.copy(
                            videoState = VideoPlayerState.VideoState.Content(video),
                            videoListState = VideoPlayerState.VideoListState.Content(videos),
                            videoInPlaylist = inPlaylist,
                        )
                    }
                }
            }
        }
    }

    private fun setDownloadButton(videoId: Int) {
        viewModelScope.launch {
            fileDownloader.activeDownloadsFlow.collect { downloads ->
                _uiState.update { currentState ->
                    when {
                        downloads.contains(videoId) -> {
                            currentState.copy(videoInCache = DownloadButtonState.Loading)
                        }

                        fileDownloader.fileExists(videoId.toString()) -> {
                            currentState.copy(videoInCache = DownloadButtonState.Remove)
                        }

                        else -> {
                            currentState.copy(videoInCache = DownloadButtonState.Download)
                        }
                    }
                }
            }
        }
    }

    private fun emptyEvent() {
        viewModelScope.launch {
            _snackBarEvent.emit(SnackbarEventParams(message = VIDEOS_IS_EMPTY))
        }
    }

    private fun nothingToPlayNoInternet() {
        viewModelScope.launch {
            _snackBarEvent.emit(
                SnackbarEventParams(
                    message = NO_INTERNET,
                    duration = SnackbarDuration.Short,
                )
            )
        }
    }

    private fun canselDownloadVideo(videoId: Int) {
        viewModelScope.launch {
            fileDownloader.cancelDownload(videoId)
            _uiState.update {
                it.copy(videoInCache = DownloadButtonState.Download)
            }
            _snackBarEvent.emit(SnackbarEventParams(message = DOWNLOAD_CANCEL))
        }
    }

    private fun selectVideo(video: Video) {
        _uiState.update {
            it.copy(videoState = VideoPlayerState.VideoState.Content(getVideoFromCache(video)))
        }
        viewModelScope.launch {
            setButtonsState()
        }
    }

    private fun getVideoFromCache(video: Video): Video {
        val url = fileDownloader.getFilePathIfExists(video.id.toString())
        return video.copy(localVideoUrl = url ?: video.remoteVideoUrl)
    }

    private fun addToPlaylist(video: Video) {
        viewModelScope.launch {
            addPlaylistVideoUseCase(video = video)
            setButtonsState()
        }
    }

    private fun removeFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video = video)
            setButtonsState()
        }
    }

    private fun downloadVideo(video: Video) {
        if (getConnected()) {
            viewModelScope.launch {
                _snackBarEvent.emit(
                    SnackbarEventParams(
                        message = VIDEO_IS_BEING_DOWNLOADED,
                        duration = SnackbarDuration.Short
                    )
                )
            }
        }
        fileDownloader.downloadFile(
            video = video,
            object : DownloadCallback {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _snackBarEvent.emit(SnackbarEventParams(message = VIDEO_DOWNLOADED))
                    }
                }

                override fun onError(error: FileDownloaderError) {
                    viewModelScope.launch {
                        when (error) {
                            FileDownloaderError.WIFI_ERROR -> {
                                _snackBarEvent.emit(
                                    SnackbarEventParams(
                                        message = error.error,
                                        actionLabel = ACTION_LABEL_DOWNLOAD,
                                        onAction = {
                                            settingsToolsUseCase.setWifiFlag(false)
                                            downloadVideo(video = video)
                                        }
                                    )
                                )
                            }

                            FileDownloaderError.NETWORK_ERROR -> {
                                _snackBarEvent.emit(
                                    SnackbarEventParams(
                                        message = error.error,
                                        actionLabel = UPDATE,
                                        onAction = { downloadVideo(video = video) }
                                    )
                                )
                            }

                            else -> _snackBarEvent.emit(SnackbarEventParams(message = error.error))
                        }
                    }
                }
            }
        )
    }

    private fun removeVideoFromCache(video: Video) {
        deleteJob = viewModelScope.launch {
            _snackBarEvent.emit(
                (SnackbarEventParams(
                    message = REMOVE,
                    actionLabel = CANCEL,
                    onAction = { deleteJob.cancel() }))
            )
            delay(4000L)
            fileDownloader.deleteFile(fileName = video.id.toString()).let {
                deleteSavedVideoUseCase(video)
                setButtonsState()
            }
            deleteJob.cancel()
        }
    }

    private suspend fun setButtonsState() {
        val state = _uiState.value
        val newUiState = if (state.videoState is VideoPlayerState.VideoState.Content) {
            val id = state.videoState.video.id

            val videoInCacheState = when {
                fileDownloader.checkDownloadingProcess(videoId = id) ->
                    DownloadButtonState.Loading

                fileDownloader.fileExists(id.toString()) ->
                    DownloadButtonState.Remove

                else ->
                    DownloadButtonState.Download
            }

            state.copy(
                videoInPlaylist = existsPlaylistVideoByIdUseCase(id),
                videoInCache = videoInCacheState
            )
        } else {
            state.copy(
                videoInPlaylist = false,
                videoInCache = DownloadButtonState.Download
            )
        }

        _uiState.value = newUiState
    }

    companion object {
        private const val VIDEO_IS_BEING_DOWNLOADED = "Видео скачивается"
        private const val VIDEO_DOWNLOADED = "Видео скачено"
        private const val ACTION_LABEL_DOWNLOAD = "Скачать"
        private const val REMOVE = "Видео удаляется"
        private const val CANCEL = "Отмена"
        private const val DOWNLOAD_CANCEL = "Загрузка видео была отменена"
        private const val VIDEOS_IS_EMPTY = "Видео пока недоступно, загляните позже"
        private const val NO_INTERNET = "Нет интернета"
        private const val UPDATE = "Обновить"
    }
}