package com.example.bodybalance.videoplayer.presentation

import androidx.annotation.OptIn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayerScreenRoute(
    routeLabel: VideoPlayerNavigateScreenId,
    itemId: Int,
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        when (routeLabel) {
            VideoPlayerNavigateScreenId.CATEGORY -> viewModel.getVideo(itemId)
            VideoPlayerNavigateScreenId.PLAYLIST -> viewModel.getPlaylistVideos(itemId)
            VideoPlayerNavigateScreenId.DOWNLOADED -> viewModel.getAllDownloadedVideos(itemId)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { params ->
            snackbarJob?.cancel()
            snackbarJob = launch {
                val result = snackbarHostState.showSnackbar(
                    message = params.message,
                    actionLabel = params.actionLabel,
                    duration = params.duration
                )

                if (result == SnackbarResult.ActionPerformed) {
                    params.onAction?.invoke()
                }
            }
        }
    }

    val videoState = when (currentState.videoState) {
        is VideoPlayerState.VideoState.Content -> currentState.videoState.video
        is VideoPlayerState.VideoState.Empty -> Video.emptyVideo(0)
    }

    VideoPlayerScreen(
        snackBarHostState = snackbarHostState,
        modifier = modifier,
        currentVideo = videoState,
        videoListState = currentState.videoListState,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        onItemSelected = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.ChoiceVideo(video = it)
            )
        },
        onClickDownload = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.DownloadVideo(video = videoState)
            )
        },
        removeVideoFromCache = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.RemoveVideoFromCache(
                    video = videoState
                )
            )
        },
        onClickAddToPlaylist = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.AddToPlaylist(video = videoState)
            )
        },
        onClickRemoveFromPlaylist = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.RemoveFromPlaylist(video = videoState)
            )
        },
        onClickCancelDownload = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.CanselDownloadVideo(videoId = videoState.id)
            )
        },
        videoInCache = currentState.videoInCache,
        videoInPlaylist = currentState.videoInPlaylist
    )
}