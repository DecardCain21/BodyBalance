package com.example.bodybalance.introduction.presentation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Introduction(
    modifier: Modifier = Modifier,
    test: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current  // Проверка на режим Preview

    Column(modifier = modifier) {
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
            ExoPlayer()
        }

        BasicButton(
            text = "Done!", onClick = { test() }, modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

//    Column {
//        ExoPlayer()
////        Player(
////            video = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
////            playingIndex = remember { mutableIntStateOf(0) }
////        )
//        BasicButton(text = "to test", onClick = { test() })
//    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    Introduction(
        modifier = Modifier.fillMaxSize(),
        test = { Log.d("Preview", "Test button clicked") }
    )
}