package com.example.bodybalance.introduction.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Introduction(
    modifier: Modifier = Modifier,
    test: () -> Unit = {}
) {

    Column {
        ExoPlayer()
//        Player(
//            video = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
//            playingIndex = remember { mutableIntStateOf(0) }
//        )
        BasicButton(text = "to test", onClick = { test() })
    }
}