package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

data class VideoPlayerState(
    val videoState: VideoState,
    val videoListState: VideoListState,
    val videoInCache: Boolean = false,
    val videoInPlaylist: Boolean = false
) {
    sealed interface VideoState {
        data object Empty : VideoState
        data class Content(val video: Video) : VideoState
    }

    sealed interface VideoListState {
        data object Empty : VideoListState
        data class Content(val videoList: List<Video>) : VideoListState
    }
}
