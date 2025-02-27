package com.example.bodybalance.videoplayer.presentation

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.bodybalance.core.data.network.NetworkError
import com.example.bodybalance.core.util.ExoPlayerCache
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
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
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoPlayerState>(VideoPlayerState.Loading)
    val uiState: StateFlow<VideoPlayerState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun getVideo(category: String) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            val result = getVideoUseCase(category)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> VideoPlayerState.Empty

                else -> result.getOrNull()?.let {
                    VideoPlayerState.Content(
                        videoUrl = it.videoItems.map { video -> video.url }.first(),
                        videoList = it.videoItems
                    )
                } ?: VideoPlayerState.Empty
            }
            _uiState.value = newState
        }
    }

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