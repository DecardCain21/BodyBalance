package com.example.bodybalance.category.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun Category(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = uiState) {
        is CategoryState.Content -> CategoryContentScreen(
            category = currentState.category,
            navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
            navigateToSettingsScreen = { navigateToSettingsScreen() }
        )

        is CategoryState.Error -> CategoryErrorScreen()
        is CategoryState.Loading -> CategoryScreenLoading()
    }
}

@Composable
fun CategoryContentScreen(
    modifier: Modifier = Modifier,
    category: List<String>,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    Box {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            /*.align(Alignment.CenterHorizontally)*/
        ) {
            items(category) {
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
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { navigateToSettingsScreen() }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Localized description",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CategoryErrorScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.error))
}

@Composable
private fun CategoryScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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