package com.example.bodybalance.videoplayer.presentation

import androidx.annotation.OptIn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
fun VideoPlayerScreenRoute(
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
        viewModel.snackBarEvent.collect { params ->
            snackbarJob?.cancel()
            snackbarJob = launch {
                snackbarHostState.showSnackbar(
                    message = params.message,
                    actionLabel = params.actionLabel,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    val videoState = when (currentState.videoState) {
        is VideoPlayerState.VideoState.Content -> currentState.videoState.video
        VideoPlayerState.VideoState.Empty -> Video.emptyVideo(0)
    }

    val videoListState = when (currentState.videoListState) {
        is VideoPlayerState.VideoListState.Content -> currentState.videoListState.videoList
        VideoPlayerState.VideoListState.Empty -> emptyList()
    }

    VideoPlayerScreen(
        routeLabel = routeLabel,
        snackBarHostState = snackbarHostState,
        itemId = itemId,
        modifier = modifier,
        videoState = videoState,
        videoListState = videoListState,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        currentState = currentState,
        getVideo = { viewModel.getVideo(itemId) },
        getPlaylistVideos = { viewModel.getPlaylistVideos(itemId) },
        onItemSelected = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.ChoiceVideo(video = it)
            )
        },
        onClickDownload = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.DownloadVideo(
                    url = videoState.url,
                    fileName = videoState.id.toString()
                )
            )
        },
        removeVideoFromCache = {
            viewModel.handleEvent(
                VideoPlayerScreenUiEvent.RemoveVideoFromCache(
                    fileName = videoState.id.toString()
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
        }
    )
}