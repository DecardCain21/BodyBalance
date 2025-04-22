package com.example.bodybalance.category.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.category.presentation.state.CategoryState
import com.example.bodybalance.core.composable.items.ExerciseItem
import com.example.bodybalance.core.composable.items.VideoItem
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.TabRowDividerColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    uiState: CategoryState,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    when (uiState) {
        is CategoryState.Content -> CategoryContentScreen(
            modifier = modifier,
            exercise = uiState.category,
            savedVideo = uiState.savedVideo,
            navigateToVideoPlayerScreen = { navigateToVideoPlayerScreen(it) },
            navigateToSettingsScreen = { navigateToSettingsScreen() },
            navigateBackToIntroduction = { navigateBackToIntroduction() }
        )

        is CategoryState.Error -> CategoryErrorScreen(modifier = modifier)
        is CategoryState.Loading -> CategoryScreenLoading(modifier = modifier)
    }
}

@Composable
private fun CategoryContentScreen(
    modifier: Modifier = Modifier,
    exercise: List<String>,
    savedVideo: List<Video>,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    Header(
        modifier = modifier,
        exercise = exercise,
        playlist = savedVideo,
        navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
        navigateToSettingsScreen = navigateToSettingsScreen,
        navigateBackToIntroduction = navigateBackToIntroduction
    )
}

@Composable
private fun Header(
    modifier: Modifier, exercise: List<String>,
    playlist: List<Video>,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    val tabs = listOf("Плейлист", "Упражнения")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            navigateToSettingsScreen = navigateToSettingsScreen,
            navigateBackToIntroduction = navigateBackToIntroduction
        )
        CategoryPages(pagerState, tabs, scope, exercise, playlist)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(true) }

    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    isClickable = false
                    navigateBackToIntroduction()
                },
                enabled = isClickable
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                    contentDescription = "Button back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = { showBottomSheet = true })
            {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { navigateToSettingsScreen() }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        })

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outline)
            }
        ) {
            Column {
                // ChangeUserBlock()
            }
        }
    }
}

@Composable
private fun CategoryPages(
    pagerState: PagerState,
    tabs: List<String>,
    scope: CoroutineScope,
    exercise: List<String>,
    playlist: List<Video>
) {
    TabRow(
        containerColor = MaterialTheme.colorScheme.background,
        divider = {
            HorizontalDivider(
                thickness = 1.dp,
                color = TabRowDividerColor
            )
        },
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .padding(horizontal = 60.dp)
                    .clip(RoundedCornerShape(topStart = 50f, topEnd = 50f)),
                height = 3.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = { Text(text = title, fontWeight = FontWeight(700), fontSize = 14.sp) }
            )
        }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> PlaylistScreen(savedVideo = playlist)
            1 -> ExerciseScreen(category = exercise)
            else -> Text("Неизвестная страница")
        }
    }
}

@Composable
private fun ExerciseScreen(
    category: List<String>,
    modifier: Modifier = Modifier
) {
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
private fun PlaylistScreen(
    savedVideo: List<Video>,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var videoToDelete by remember { mutableStateOf<Video?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = savedVideo, key = { it.id }) { item ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.EndToStart) {
                            videoToDelete = item
                            showDialog = true
                            false
                        } else {
                            false
                        }
                    },
                    positionalThreshold = { distance -> distance * 0.6f }
                )

                if (showDialog && videoToDelete == item) {
                    LaunchedEffect(showDialog) {
                        if (!showDialog) {
                            dismissState.reset()
                        }
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false, // Отключаем свайп вправо
                    backgroundContent = { DismissBackground() },
                    content = {
                        VideoItem(
                            title = item.title,
                            showIconDrag = true
                        )
                    }
                )
            }
        }
    }

    if (showDialog) {
        DeleteVideoDialog(
            onDismiss = {
                showDialog = false
                videoToDelete = null
            },
            onConfirm = {
                videoToDelete?.let { video ->
                    // todo: вызвать функцию удаления видео
                }
                showDialog = false
                videoToDelete = null
            }
        )
    }
}

@Composable
private fun DismissBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White,
        )
    }
}

@Composable
private fun DeleteVideoDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                modifier = Modifier.padding(end = 30.dp),
                text = stringResource(R.string.delete_video_from_playlist),
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = stringResource(R.string.delete_video_dilog_message),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = stringResource(R.string.remove))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.cansel))
            }
        }
    )
}

@Composable
private fun CategoryErrorScreen(
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(R.string.error))
}

@Composable
private fun CategoryScreenLoading(
    modifier: Modifier = Modifier
) {
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
            savedVideo = listOf(
                Video(
                    title = "Разминка перед упражнениями на отдельную группу мыщц",
                    url = "",
                    id = 0.0,
                    description = "321"
                ),
                Video(title = "\"Название видео\"", url = "", id = 0.0, description = "321"),
                Video(title = "\"Название видео\"", url = "", id = 0.0, description = "321")
            ),
            navigateToVideoPlayerScreen = {},
            navigateBackToIntroduction = {})
    }
}