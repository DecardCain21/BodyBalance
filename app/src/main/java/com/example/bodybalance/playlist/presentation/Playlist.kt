package com.example.bodybalance.playlist.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun Playlist(
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel = hiltViewModel(),
    navigateToVideoPlayerScreen: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        /*.align(Alignment.CenterHorizontally)*/
    ) {
        items(playlistViewModel.setContent()) {
            //Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { navigateToVideoPlayerScreen(it) },
                shape = RectangleShape,
                modifier = Modifier
                    .width(250.dp)
                    .height(100.dp)
            ) {
                Text(text = it)
            }
        }
    }

}

@Preview
@Composable
private fun PreviewPlaylist(
    modifier: Modifier = Modifier,
) {
    BodyBalanceTheme(dynamicColor = false) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(listOf("123", "321")) {
                Button(
                    onClick = {}, shape = RectangleShape, modifier = Modifier
                        .width(250.dp)
                        .height(100.dp)
                ) {
                    Text(text = it)
                }
            }
        }
    }
}