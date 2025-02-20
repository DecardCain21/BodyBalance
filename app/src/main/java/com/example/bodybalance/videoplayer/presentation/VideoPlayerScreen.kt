package com.example.bodybalance.videoplayer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer
import com.example.bodybalance.core.data.storage.FileDownloader

@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current  // Проверка на режим Preview

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreview) {
            // Заглушка вместо ExoPlayer в Preview
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
            // Основной ExoPlayer для реального запуска
            val downloader = FileDownloader(LocalContext.current)
            downloader.downloadFile(
                "https://github.com/DecardCain21/BodyBalance/raw/refs/heads/dev/request/videoEscapes.mp4",
                "videoEscapes.mp4"
            )
            ExoPlayer(url = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        }

        BasicButton(
            text = "Done!", onClick = { }, modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    VideoPlayerScreen(
        modifier = Modifier.fillMaxSize(),
    )
}