package com.example.bodybalance.videoplayer.presentation

import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BodyBalanceActionButton
import com.example.bodybalance.core.composable.exoPlayer
import com.example.bodybalance.core.composable.items.VideoItem
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.state.VideoPlayerState

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    category: String,
    modifier: Modifier = Modifier,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) { viewModel.getVideo(category) }


    when (currentState) {
        is VideoPlayerState.Content -> {
            VideoPlayerScreenContent(
                modifier = modifier,
                video = currentState.currentVideo,
                videoList = currentState.videoList,
                onItemSelected = {
                    viewModel.handleEvent(
                        VideoPlayerScreenUiEvent.ChoiceVideo(
                            video = it
                        )
                    )
                },
                onClickDownload = {
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
                    with(currentState.currentVideo) {
                        viewModel.handleEvent(
                            VideoPlayerScreenUiEvent.RemoveVideoFromCache(
                                fileName = id.toString()
                            )
                        )
                    }
                },
                onClickAddToPlaylist = {
                    with(currentState.currentVideo) {
                        viewModel.handleEvent(
                            VideoPlayerScreenUiEvent.AddToPlaylist(
                                video = this
                            )
                        )
                    }
                },
                onClickRemoveFromPlaylist = {
                    with(currentState.currentVideo) {
                        viewModel.handleEvent(
                            VideoPlayerScreenUiEvent.RemoveFromPlaylist(
                                video = this
                            )
                        )
                    }
                },
                isDownloadState = currentState.videoInCache,
                isAddPlaylist = currentState.videoInPlaylist
            )
        }

        is VideoPlayerState.Loading -> VideoPlayerScreenLoading()
        is VideoPlayerState.Empty -> Unit
    }
}

@Composable
private fun VideoPlayerScreenContent(
    video: Video,
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier,
    onClickDownload: () -> Unit,
    removeVideoFromCache: () -> Unit,
    onClickAddToPlaylist: () -> Unit,
    onClickRemoveFromPlaylist: () -> Unit,
    isDownloadState: Boolean,
    isAddPlaylist: Boolean
) {
    val isPreview = LocalInspectionMode.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseTopAppBar(navigateBack = {})

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
            ExoPlayer(video = video)
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = video.title,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(400),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary
        )
        //NavItem(videoList = videoList, onItemSelected = { onItemSelected(it) })
        Row() {
            if (isDownloadState) {
                BodyBalanceActionButton(
                    onClick = { removeVideoFromCache() },
                    text = "Удалить с устройства",
                    imageVector = Icons.Default.DeleteOutline
                )
            } else {
                BodyBalanceActionButton(
                    onClick = { onClickDownload() },
                    text = "Скачать",
                    imageVector = Icons.Default.Download
                )
            }
            if (isAddPlaylist) {
                BodyBalanceActionButton(
                    onClick = { onClickRemoveFromPlaylist() },
                    text = "Добавлено в плейлист",
                    imageVector = Icons.Default.Bookmark
                )
            } else {
                BodyBalanceActionButton(
                    onClick = { onClickAddToPlaylist() },
                    text = "Добавить в плейлист",
                    imageVector = Icons.Default.BookmarkBorder
                )
            }
        }
        VideoList(videoList = videoList, onItemSelected = onItemSelected)
    }
}

@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VideoList(
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectItemIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = rememberLazyListState()
    ) {
        itemsIndexed(videoList) { index, item ->
            VideoItem(
                title = item.title,
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

@Composable
private fun ExoPlayer(modifier: Modifier = Modifier, video: Video) {
    exoPlayer(
        modifier = modifier,
        context = LocalContext.current,
        video = video
    )
}

@Composable
private fun VideoPlayerScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF141218)
@Composable
fun IntroductionPreview() {
    BodyBalanceTheme {
        Box {
            VideoPlayerScreenContent(
                video = Video.emptyVideo(),
                videoList = listOf(Video.emptyVideo(), Video.emptyVideo()),
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
}