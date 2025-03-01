package com.example.bodybalance.introduction.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer
import com.example.bodybalance.videoplayer.presentation.VideoPlayerViewModel

const val INTRODUCTION = "Introduction"

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Introduction(
    modifier: Modifier = Modifier,
    navToPlaylist: () -> Unit = {},
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    val isPreview = LocalInspectionMode.current

    LaunchedEffect(Unit) { viewModel.getVideo(INTRODUCTION) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            viewModel.playVideo("https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            ExoPlayer(exoPlayer = viewModel.exoPlayer)
        }

        BasicButton(
            text = "Done!", onClick = { navToPlaylist() }, modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    Introduction(
        modifier = Modifier.fillMaxSize(),
        navToPlaylist = { Log.d("Preview", "Test button clicked") }
    )
}