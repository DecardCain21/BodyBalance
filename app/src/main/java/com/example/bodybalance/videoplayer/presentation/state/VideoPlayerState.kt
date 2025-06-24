package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

internal data class VideoPlayerState(
    val cacheSize: Long = 0L,
    val videoState: VideoState,
    val videoListState: VideoListState,
    val videoInCache: DownloadButtonState = DownloadButtonState.Download, //пока что поставлю дефолтное значение
    val videoInPlaylist: Boolean = false
) {

    sealed interface DownloadButtonState {
        data object Loading : DownloadButtonState
        data object Download : DownloadButtonState
        data object Remove : DownloadButtonState
    }

    sealed interface VideoState {
        data object Empty : VideoState
        data class Content(val video: Video) : VideoState
    }

    sealed interface VideoListState {
        data object Empty : VideoListState
        data object Loading : VideoListState
        data class Content(val videoList: List<Video>) : VideoListState
    }

    companion object {
        fun emptyState(): VideoPlayerState =
            VideoPlayerState(
                videoState = VideoState.Empty,
                videoListState = VideoListState.Loading
            )
    }
}