package com.example.bodybalance

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer

@Composable
fun Test(modifier: Modifier = Modifier) {
    Column {
        ExoPlayer()
        BasicButton(text = "test", onClick = {  })
    }
}