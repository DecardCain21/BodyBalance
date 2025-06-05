package com.example.bodybalance.videoplayer.presentation

import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BodyBalanceActionButton
import com.example.bodybalance.core.composable.exoPlayer
import com.example.bodybalance.core.composable.items.VideoItem
import com.example.bodybalance.core.composable.snackbar.CustomSnackbarHost
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayerScreen(
    routeLabel: VideoPlayerNavigateScreenId,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    videoState: Video,
    videoListState: List<Video>,
    navigateBackToPlaylistScreen: () -> Unit,
    currentState: VideoPlayerState,
    getVideo: () -> Unit,
    getPlaylistVideos: () -> Unit,
    getDownloadedVideos: () -> Unit,
    onItemSelected: (Video) -> Unit,
    onClickDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
) {

    LaunchedEffect(Unit) {
        when (routeLabel) {
            VideoPlayerNavigateScreenId.CATEGORY -> getVideo()
            VideoPlayerNavigateScreenId.PLAYLIST -> getPlaylistVideos()
            VideoPlayerNavigateScreenId.DOWNLOADED -> getDownloadedVideos()
        }
    }

    VideoPlayerScreenContent(
        modifier = modifier,
        snackBarHostState = snackBarHostState,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        video = videoState,
        videoList = videoListState,
        onItemSelected = { onItemSelected(it) },
        onClickDownload = { onClickDownload() },
        removeVideoFromCache = { removeVideoFromCache() },
        onClickAddToPlaylist = { onClickAddToPlaylist() },
        onClickRemoveFromPlaylist = { onClickRemoveFromPlaylist() },
        isDownloadState = currentState.videoInCache,
        isAddPlaylist = currentState.videoInPlaylist
    )
}

@Composable
private fun VideoPlayerScreenContent(
    video: Video,
    snackBarHostState: SnackbarHostState,
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    onClickDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
    isDownloadState: Boolean,
    isAddPlaylist: Boolean
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        topBar = {
            if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                BaseTopAppBar(navigateBack = { navigateBackToPlaylistScreen() })
            }
        },
        snackbarHost = {
            CustomSnackbarHost(
                modifier = Modifier.padding(horizontal = 8.dp),
                hostState = snackBarHostState
            )
        }
    ) { paddingValue ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderVideoPlayerScreen(video = video)
            BodyVideoPlayerScreen(
                isAddPlaylist = isAddPlaylist,
                isDownloadState = isDownloadState,
                onClickDownload = onClickDownload,
                removeVideoFromCache = removeVideoFromCache,
                onClickAddToPlaylist = onClickAddToPlaylist,
                onClickRemoveFromPlaylist = onClickRemoveFromPlaylist
            )
            if (videoList.isNotEmpty()) {
                VideoList(
                    videoList = videoList,
                    onItemSelected = onItemSelected,
                    currentVideo = video
                )
            }
        }
    }
}

@Composable
private fun HeaderVideoPlayerScreen(video: Video) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .aspectRatio(3 / 4f)
                .padding(top = 50.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("ExoPlayer Placeholder", color = Color.White)
        }
    } else {
        exoPlayer(
            context = LocalContext.current,
            video = video
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = video.name,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight(400),
        fontSize = 22.sp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun BodyVideoPlayerScreen(
    isAddPlaylist: Boolean,
    isDownloadState: Boolean,
    onClickDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
        item {
            if (isDownloadState) {
                BodyBalanceActionButton(
                    onClick = {
                        removeVideoFromCache()
                    },
                    text = stringResource(R.string.remove_from_device),
                    imageVector = Icons.Default.DeleteOutline
                )
            } else {
                BodyBalanceActionButton(
                    onClick = onClickDownload,
                    text = stringResource(R.string.download),
                    imageVector = Icons.Default.Download
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            if (isAddPlaylist) {
                BodyBalanceActionButton(
                    onClick = { onClickRemoveFromPlaylist() },
                    text = stringResource(R.string.added_to_playlist),
                    imageVector = Icons.Default.Bookmark
                )
            } else {
                BodyBalanceActionButton(
                    onClick = { onClickAddToPlaylist() },
                    text = stringResource(R.string.add_to_playlist),
                    imageVector = Icons.Default.BookmarkBorder
                )
            }
        }
    }
}

@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VideoList(
    currentVideo: Video,
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentIndex = videoList.indexOfFirst { it.id == currentVideo.id }
    var selectItemIndex by rememberSaveable { mutableIntStateOf(currentIndex) }
    val scrollStateOfVideo = rememberLazyListState()

    LaunchedEffect(videoList) { scrollStateOfVideo.animateScrollToItem(selectItemIndex) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = scrollStateOfVideo
    ) {
        itemsIndexed(videoList) { index, item ->
            VideoItem(
                title = item.name,
                showIconDrag = false,
                showSelectItem = selectItemIndex == index,
                modifier = Modifier
                    .combinedClickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            selectItemIndex = index
                            onItemSelected(item)
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun IntroductionPreview() {
    BodyBalanceTheme {
        VideoPlayerScreenContent(
            video = Video.emptyVideo(1),
            snackBarHostState = SnackbarHostState(),
            videoList = listOf(Video.emptyVideo(1), Video.emptyVideo(2)),
            navigateBackToPlaylistScreen = {},
            onItemSelected = {},
            onClickDownload = {},
            onClickAddToPlaylist = {},
            onClickRemoveFromPlaylist = {},
            removeVideoFromCache = {},
            isDownloadState = false,
            isAddPlaylist = false
        )
    }
}