package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

internal interface VideoPlayerScreenUiEvent {

    data class ChoiceVideo(val video: Video) : VideoPlayerScreenUiEvent

    data class DownloadVideo(val video: Video) : VideoPlayerScreenUiEvent

    data class RemoveVideoFromCache(val video: Video) : VideoPlayerScreenUiEvent

    data class AddToPlaylist(val video: Video) : VideoPlayerScreenUiEvent

    data class RemoveFromPlaylist(val video: Video) : VideoPlayerScreenUiEvent

    data class CanselDownloadVideo(val videoId: Int): VideoPlayerScreenUiEvent
}