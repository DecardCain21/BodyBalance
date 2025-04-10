package com.example.bodybalance.category.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    uiState: CategoryState,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    when (uiState) {
        is CategoryState.Content -> CategoryContentScreen(
            modifier = modifier,
            exercise = uiState.category,
            playlist = listOf(),
            navigateToVideoPlayerScreen = { navigateToVideoPlayerScreen(it) },
            navigateToSettingsScreen = { navigateToSettingsScreen() }
        )

        is CategoryState.Error -> CategoryErrorScreen(modifier = modifier)
        is CategoryState.Loading -> CategoryScreenLoading(modifier = modifier)
    }
}

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryContentScreen(
    modifier: Modifier = Modifier,
    exercise: List<String>,
    playlist: List<String>,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    val tabs = listOf("Плейлист", "Упражнения")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {

            },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                        contentDescription = "Button back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    modifier = modifier/*.align(Alignment.TopEnd)*/,
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = modifier/*.align(Alignment.TopEnd)*/,
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            })

        // Верхняя панель вкладок
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            // Ваши табы здесь
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }

        // Контент страниц
        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> PlaylistScreen(category = playlist)
                1 -> ExerciseScreen(category = exercise)
                else -> Text("Неизвестная страница")
            }
        }
    }
}

@Composable
fun ExerciseScreen(modifier: Modifier = Modifier, category: List<String>) {
    Box {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(category) {
                //Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { },
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
}

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier, category: List<String>) {
    Box {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(category) {
                //Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { },
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
        CategoryContentScreen(
            navigateToSettingsScreen = {},
            exercise = listOf(),
            playlist = listOf(),
            navigateToVideoPlayerScreen = {})
    }
}