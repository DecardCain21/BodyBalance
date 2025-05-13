package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

sealed interface VideoPlayerState {

    data object Empty : VideoPlayerState
    data object Loading : VideoPlayerState
    data class Content(
        val currentVideo: Video,
        val videoList: List<Video>,
        val videoInCache: Boolean = false,
        val videoInPlaylist: Boolean = false
    ) : VideoPlayerState
}