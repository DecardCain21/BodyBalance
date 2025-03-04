package com.example.bodybalance.category.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun Category(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    navigateToVideoPlayerScreen: (String) -> Unit
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = uiState) {
        is CategoryState.Content -> CategoryContentScreen(
            category = currentState.category,
            navigateToVideoPlayerScreen = navigateToVideoPlayerScreen
        )
        is CategoryState.Error -> CategoryErrorScreen()
        is CategoryState.Loading -> CategoryScreenLoading()
    }
}

@Composable
fun CategoryContentScreen(
    modifier: Modifier = Modifier,
    category: List<String>,
    navigateToVideoPlayerScreen: (String) -> Unit
) {
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
}

@Composable
fun CategoryErrorScreen(modifier: Modifier = Modifier) {
    Text(text = "Ошибка")
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