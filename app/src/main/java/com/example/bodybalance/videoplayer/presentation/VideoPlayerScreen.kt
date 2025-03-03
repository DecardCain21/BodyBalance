package com.example.bodybalance.videoplayer.presentation

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.NavItem
import com.example.bodybalance.core.data.storage.FileDownloader
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.player.composable.ExoPlayer
import java.io.File

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
    category: String
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) { viewModel.getVideo(category) }

    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 4f)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("ExoPlayer Placeholder", color = Color.White)
        }
    } else {
        when (currentState) {
            is VideoPlayerState.Content -> {
                VideoPlayerScreenContent(
                    modifier = modifier,
                    url = currentState.currentVideoUrl,
                    videoList = currentState.videoList,
                    onItemSelected = { viewModel.selectVideo(it) }
                )
            }

            is VideoPlayerState.Loading -> VideoPlayerScreenLoading()
            is VideoPlayerState.Empty -> Unit
        }
    }
}

@Composable
fun VideoPlayerScreenContent(
    modifier: Modifier = Modifier,
    url: String,
    videoList: List<Video>,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExoPlayer(url = url)
        BasicButton(
            text = "Done!", onClick = { }, modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)
        )
        NavItem(videoList = videoList, onItemSelected = { onItemSelected(it) })
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

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    VideoPlayerScreen(
        modifier = Modifier.fillMaxSize(),
        category = "Шея"
    )
}