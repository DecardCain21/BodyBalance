package com.example.bodybalance.videoplayer.presentation

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreenRoute(
    routeLabel: VideoPlayerNavigateScreenId,
    itemId: Int,
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    VideoPlayerScreen(
        routeLabel = routeLabel,
        itemId = itemId,
        modifier = modifier,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        currentState = currentState,
        getVideo = { viewModel.getVideo(itemId) },
        getPlaylistVideos = { viewModel.getPlaylistVideos(itemId) },
        onItemSelected = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.ChoiceVideo(
                    video = it
                )
            )
        },
        onClickDownload = {
            if (currentState is VideoPlayerState.Content)
                with(currentState.currentVideo) {
                    viewModel.handleEvent(
                        VideoPlayerScreenUiEvent.DownloadVideo(
                            url = url,
                            fileName = id.toString()
                        )
                    )
                }
        },
        removeVideoFromCache = {
            if (currentState is VideoPlayerState.Content)
                with(currentState.currentVideo) {
                    viewModel.handleEvent(
                        VideoPlayerScreenUiEvent.RemoveVideoFromCache(
                            fileName = id.toString()
                        )
                    )
                }
        },
        onClickAddToPlaylist = {
            if (currentState is VideoPlayerState.Content)
                with(currentState.currentVideo) {
                    viewModel.handleEvent(
                        VideoPlayerScreenUiEvent.AddToPlaylist(
                            video = this
                        )
                    )
                }
        },
        onClickRemoveFromPlaylist = {
            if (currentState is VideoPlayerState.Content)
                with(currentState.currentVideo) {
                    viewModel.handleEvent(
                        VideoPlayerScreenUiEvent.RemoveFromPlaylist(
                            video = this
                        )
                    )
                }
        }

    )
}