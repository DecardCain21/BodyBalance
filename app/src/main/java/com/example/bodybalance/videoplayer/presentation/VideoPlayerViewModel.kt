package com.example.bodybalance.videoplayer.presentation

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.bodybalance.core.data.dto.ItemDto
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.util.ExoPlayerCache
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.SaveVideoInCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase,
    private val saveVideoInCacheUseCase: SaveVideoInCacheUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerState>(testStateContent())
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun getVideo() {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            //val result: Result<VideoResponse> = getVideoUseCase()
            //saveVideoInCache.invoke(Video(1, "testUrl", "Test"))
            /*val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> VideoPlayerState.Empty

                else -> result.getOrNull()?.let {
                    VideoPlayerState.Content(
                        videoUrl = it.videoItems.map { video -> video.url }.first(),
                        videoList = it.videoItems
                    )
                } ?: VideoPlayerState.Empty
            }*/
            val newState = VideoPlayerState.Content(
                videoUrl = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                listOf(
                    ItemDto(
                        2.2,
                        url = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        "name",
                        "description"
                    )
                )
            )
            saveVideoInCacheUseCase(
                video = Video(
                    id = 2,
                    previewUrl = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    category = "test2"
                )
            )
            _uiState.value = newState
        }
    }

    private fun testStateContent() = VideoPlayerState.Content(
        "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        listOf(
            ItemDto(
                2.2,
                "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "name",
                "description"
            ),
            ItemDto(
                2.2,
                "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "name",
                "description"
            ),
            ItemDto(
                2.2,
                "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "name",
                "description"
            )
        )
    )

    @OptIn(UnstableApi::class)
    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(ExoPlayerCache.getCacheDataSourceFactory(context))
            )
            .build()
    }

    fun playVideo(url: String) {
        with(exoPlayer) {
            val currentMediaUrl = currentMediaItem?.localConfiguration?.uri.toString()
            if (currentMediaUrl != url) {
                playWhenReady = false
                val mediaItem = MediaItem.fromUri(url)
                setMediaItem(mediaItem)
                prepare()
            }
        }
    }

    fun selectVideo(url: String) {
        _uiState.update {
            (it as VideoPlayerState.Content).copy(videoUrl = url)
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}