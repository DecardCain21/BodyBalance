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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.composable.BodyBalanceActionButton
import com.example.bodybalance.core.composable.exoPlayer
import com.example.bodybalance.core.composable.items.ExerciseItem
import com.example.bodybalance.core.data.service.FileDownloader
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import java.io.File

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
                onItemSelected = { viewModel.selectVideo(it) }
            )
        }

        is VideoPlayerState.Loading -> VideoPlayerScreenLoading()
        is VideoPlayerState.Empty -> Unit
    }

}

@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VideoPlayerScreenContent(
    video: Video,
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    val isPreview = LocalInspectionMode.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .padding(26.dp)
                .align(Alignment.Start),
            onClick = {
                /*navigateBackToIntroduction()*/
            },
            enabled = false /*isClickable*/
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                contentDescription = "Button back",
                tint = Color.White
            )
        }
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
                .padding(all = 16.dp),
            text = video.title,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(400),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary
        )
        //NavItem(videoList = videoList, onItemSelected = { onItemSelected(it) })
        Row() {
            BodyBalanceActionButton(
                onClick = {},
                text = "Скачать",
                imageVector = Icons.Default.Download
            )
            BodyBalanceActionButton(
                onClick = {},
                text = "Добавить в плейлист",
                imageVector = Icons.Default.BookmarkBorder
            )

        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth().padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            state = rememberLazyListState()
        ) {
            items(videoList) { item ->
                ExerciseItem(modifier = Modifier.combinedClickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onItemSelected(item) }
                ), title = item.title)
            }
        }
    }
}

@Composable
private fun VideoPlayerScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun fileExist(viewModel: VideoPlayerViewModel) {
    //"/data/data/com.example.bodybalance/files/videoSaved"
    val fileName = "videoSaved"
    val filePath = "${LocalContext.current.filesDir.path}/$fileName.mp4"
    val file = File(filePath)
    if (file.exists()) {
        // ExoPlayer(exoPlayer = viewModel.exoPlayer)
    } else {
        println("Файл не найден: $filePath")
    }
}

@Composable
private fun downloadVideo(url: String, fileName: String) {
    val downloader = FileDownloader(LocalContext.current)
    downloader.downloadFile(
        url = url,
        fileName = fileName
    )
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
            )
        }
    }
}