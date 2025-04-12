package com.example.bodybalance.category.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.ExerciseItem
import com.example.bodybalance.core.composable.VideoItem
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.CoroutineScope
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

        is CategoryState.Error -> CategoryErrorScreen()
        is CategoryState.Loading -> CategoryScreenLoading(modifier = modifier)
    }
}

@Composable
fun CategoryContentScreen(
    modifier: Modifier = Modifier,
    exercise: List<String>,
    playlist: List<String>,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    Header(
        modifier = modifier,
        exercise = exercise,
        playlist = playlist
    )
}

@Composable
fun Header(
    modifier: Modifier, exercise: List<String>,
    playlist: List<String>
) {
    val tabs = listOf("Плейлист", "Упражнения")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        BodyBalanceTopAppBar()
        BodyBalancePages(pagerState, tabs, scope, exercise, playlist)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyBalanceTopAppBar() {
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
                onClick = { }) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        })
}

@Composable
fun BodyBalancePages(
    pagerState: PagerState,
    tabs: List<String>,
    scope: CoroutineScope,
    exercise: List<String>,
    playlist: List<String>
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .padding(horizontal = 60.dp),
                height = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = { Text(title) }
            )
        }
    }

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

@Composable
fun ExerciseScreen(modifier: Modifier = Modifier, category: List<String>) {
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            state = rememberLazyListState()
        ) {
            items(category) { item ->
                ExerciseItem(title = item)
            }
        }
    }
}

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier, category: List<String>) {
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(category) { item ->
                VideoItem(title = item)
            }
        }
    }
}

@Composable
fun CategoryErrorScreen() {
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
            exercise = listOf(
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3"
            ),
            playlist = listOf(
                "Разминка перед упражнениями на отдельную группу мыщц",
                "Название видео",
                "Название видео"
            ),
            navigateToVideoPlayerScreen = {})
    }
}