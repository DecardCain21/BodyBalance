package com.example.bodybalance.videoplayer.presentation

import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayerScreen(
    currentVideo: Video,
    snackBarHostState: SnackbarHostState,
    videoListState: VideoPlayerState.VideoListState,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    onClickDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
    onClickCancelDownload: () -> Unit,
    videoInCache: VideoPlayerState.DownloadButtonState,
    videoInPlaylist: Boolean,
    emptyEvent: () -> Unit,
    noInternetPlayerSnackBar: () -> Unit
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        topBar = {
            if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                BaseTopAppBar(navigateBack = {
                    navigateBackToPlaylistScreen()
                })
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
        ) {
            when (videoListState) {
                is VideoPlayerState.VideoListState.Content -> {
                    HeaderVideoPlayerScreen(
                        video = currentVideo,
                        noInternetPlayerSnackBar = noInternetPlayerSnackBar
                    )
                    BodyVideoPlayerScreen(
                        modifier = Modifier.padding(top = 12.dp),
                        isAddPlaylist = videoInPlaylist,
                        isDownloadState = videoInCache,
                        onClickDownload = onClickDownload,
                        onClickCancelDownload = onClickCancelDownload,
                        removeVideoFromCache = removeVideoFromCache,
                        onClickAddToPlaylist = onClickAddToPlaylist,
                        onClickRemoveFromPlaylist = onClickRemoveFromPlaylist
                    )
                    VideoList(
                        videoList = videoListState.videoList,
                        onItemSelected = onItemSelected,
                        currentVideo = currentVideo
                    )
                }

                is VideoPlayerState.VideoListState.Loading -> VideoListLoading()
                VideoPlayerState.VideoListState.Empty -> VideoListEmpty(emptyEvent = emptyEvent)
            }
        }
    }
}

@Composable
private fun VideoListLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun VideoListEmpty(emptyEvent: () -> Unit) {
    LaunchedEffect(Unit) { emptyEvent() }
}

@Composable
private fun HeaderVideoPlayerScreen(
    video: Video,
    noInternetPlayerSnackBar: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("ExoPlayer Placeholder", color = Color.White)
            }
        } else {
            exoPlayer(
                context = LocalContext.current,
                video = video,
                showSnackBar = noInternetPlayerSnackBar
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = video.name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.W400,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BodyVideoPlayerScreen(
    modifier: Modifier = Modifier,
    isAddPlaylist: Boolean,
    isDownloadState: VideoPlayerState.DownloadButtonState,
    onClickDownload: () -> Unit,
    onClickCancelDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            if (isAddPlaylist) {
                BodyBalanceActionButton(
                    onClick = { onClickRemoveFromPlaylist() },
                    text = stringResource(R.string.button_playlist),
                    imageVector = Icons.Default.Bookmark
                )
            } else {
                BodyBalanceActionButton(
                    onClick = { onClickAddToPlaylist() },
                    text = stringResource(R.string.button_playlist),
                    imageVector = Icons.Default.BookmarkBorder
                )
            }
        }
        item {
            when (isDownloadState) {
                VideoPlayerState.DownloadButtonState.Download -> {
                    BodyBalanceActionButton(
                        onClick = onClickDownload,
                        text = stringResource(R.string.download),
                        imageVector = Icons.Default.Download
                    )
                }

                VideoPlayerState.DownloadButtonState.Loading -> {
                    Row(
                        modifier = Modifier
                            .combinedClickable(
                                interactionSource = null,
                                indication = null,
                                onClick = { onClickCancelDownload() }
                            )
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            trackColor = Color.Black,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.cansel_download),
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.nonScaledSp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                VideoPlayerState.DownloadButtonState.Remove -> {
                    BodyBalanceActionButton(
                        onClick = removeVideoFromCache,
                        text = stringResource(R.string.remove_from_device),
                        imageVector = Icons.Default.DeleteOutline
                    )
                }
            }
        }
    }
}

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

    LaunchedEffect(videoList) {
        scrollStateOfVideo.animateScrollToItem(
            index = selectItemIndex,
            scrollOffset = 10
        )
    }

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
                imageUrl = item.imageUrl,
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
        VideoPlayerScreen(
            currentVideo = Video.emptyVideo(1)
                .copy(description = "sdfsfsfsffssdfs", name = "asfasfssf"),
            snackBarHostState = SnackbarHostState(),
            navigateBackToPlaylistScreen = {},
            onItemSelected = {},
            onClickDownload = {},
            onClickAddToPlaylist = {},
            onClickRemoveFromPlaylist = {},
            removeVideoFromCache = {},
            videoInCache = VideoPlayerState.DownloadButtonState.Download,
            videoInPlaylist = false,
            onClickCancelDownload = {},
            videoListState = VideoPlayerState.VideoListState.Content(
                listOf(
                    Video.emptyVideo(1),
                    Video.emptyVideo(2)
                )
            ),
            emptyEvent = {},
            noInternetPlayerSnackBar = {}
        )
    }
}