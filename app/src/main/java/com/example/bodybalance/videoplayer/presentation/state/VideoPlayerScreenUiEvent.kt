package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

internal interface VideoPlayerScreenUiEvent {
    data class ChoiceVideo(val video: Video) : VideoPlayerScreenUiEvent
    data class DownloadVideo(val url: String, val fileName: String) : VideoPlayerScreenUiEvent
    data class RemoveVideoFromCache(val fileName: String) : VideoPlayerScreenUiEvent
    data class AddToPlaylist(val video: Video) : VideoPlayerScreenUiEvent
    data class RemoveFromPlaylist(val video: Video) : VideoPlayerScreenUiEvent
}