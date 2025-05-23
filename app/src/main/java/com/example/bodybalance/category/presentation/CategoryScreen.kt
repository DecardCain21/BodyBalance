package com.example.bodybalance.category.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.SheetState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.items.ExerciseItem
import com.example.bodybalance.core.composable.items.VideoItem
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.TabRowDividerColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    uiState: CategoryState,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    changeUser: (Account) -> Unit,
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Double, order: Int) -> Unit
) {
    when (uiState) {
        is CategoryState.Content -> CategoryContentScreen(
            modifier = modifier,
            accounts = uiState.accounts,
            exercise = uiState.category,
            playlistVideo = uiState.playlistVideo,
            activeAccount = uiState.activeAccount,
            navigateToVideoPlayerScreen = { navigateToVideoPlayerScreen(it) },
            navigateToSettingsScreen = { navigateToSettingsScreen() },
            navigateBackToIntroduction = { navigateBackToIntroduction() },
            navigateToHomeScreen = { navigateToHomeScreen() },
            changeUser = { changeUser(it) },
            deleteVideoFromPlaylist = { deleteVideoFromPlaylist(it) },
            updateOrderPlaylistVideo = updateOrderPlaylistVideo
        )

        is CategoryState.Error -> CategoryErrorScreen(modifier = modifier)
        is CategoryState.Loading -> CategoryScreenLoading(modifier = modifier)
    }
}

@Composable
private fun CategoryContentScreen(
    accounts: List<Account>,
    exercise: List<String>,
    playlistVideo: List<Video>,
    activeAccount: Account,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit, // String - Название категории
    navigateToSettingsScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    changeUser: (Account) -> Unit,
    modifier: Modifier = Modifier,
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Double, order: Int) -> Unit
) {
    var selectedAccount by remember(accounts) {
        mutableStateOf(activeAccount)
    }

    var isFirstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(selectedAccount) {
        if (isFirstLaunch) {
            isFirstLaunch = false
        } else {
            changeUser(selectedAccount)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            accounts = accounts,
            selectedAccount = selectedAccount,
            onAccountSelected = { selectedAccount = it },
            navigateToSettingsScreen = navigateToSettingsScreen,
            navigateBackToIntroduction = navigateBackToIntroduction,
            navigateToHomeScreen = navigateToHomeScreen
        )
        CategoryPages(
            exercise = exercise,
            playlistVideo = playlistVideo,
            navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
            deleteVideoFromPlaylist = { deleteVideoFromPlaylist(it) },
            updateOrderPlaylistVideo = updateOrderPlaylistVideo
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    accounts: List<Account>,
    selectedAccount: Account,
    onAccountSelected: (Account) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    BaseTopAppBar(
        navigateBack = navigateBackToIntroduction,
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
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outline)
            }
        ) {
            ChangeUserBlock(
                accounts = accounts,
                selectedAccount = selectedAccount,
                onAccountSelected = { onAccountSelected(it) },
                onAddAccountClick = {
                    navigateToHomeScreen()
                    showBottomSheet = false
                },
                sheetState = sheetState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ChangeUserBlock(
    accounts: List<Account>,
    selectedAccount: Account,
    onAccountSelected: (Account) -> Unit,
    onAddAccountClick: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        accounts.forEach { account ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onAccountSelected(account) }
                    )
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = account.name, fontSize = 16.sp)
                RadioButton(
                    selected = selectedAccount == account,
                    onClick = { onAccountSelected(account) },
                    colors = RadioButtonColors(
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedColor = MaterialTheme.colorScheme.onPrimary,
                        disabledSelectedColor = MaterialTheme.colorScheme.onPrimary,
                        disabledUnselectedColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.add_account),
            buttonColor = MaterialTheme.colorScheme.onPrimary,
            enabledTextColor = MaterialTheme.colorScheme.primary,
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onAddAccountClick()
                    }
                }
            }
        )
    }
}

@Composable
private fun CategoryPages(
    exercise: List<String>,
    playlistVideo: List<Video>,
    navigateToVideoPlayerScreen: (String) -> Unit,
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Double, order: Int) -> Unit
) {
    val tabs = listOf("Плейлист", "Упражнения")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

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
            0 -> PlaylistScreen(
                playlistVideo = playlistVideo,
                deleteVideoFromPlaylist = { deleteVideoFromPlaylist(it) },
                updateOrderPlaylistVideo = updateOrderPlaylistVideo
            )

            1 -> ExerciseScreen(
                category = exercise,
                navigateToVideoPlayerScreen = navigateToVideoPlayerScreen
            )

            else -> Text("Неизвестная страница")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExerciseScreen(
    category: List<String>,
    modifier: Modifier = Modifier,
    navigateToVideoPlayerScreen: (String) -> Unit
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
                ExerciseItem(modifier = Modifier.combinedClickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { navigateToVideoPlayerScreen(item) }
                ), title = item)
            }
        }
    }
}

@Composable
private fun PlaylistScreen(
    playlistVideo: List<Video>,
    modifier: Modifier = Modifier,
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Double, order: Int) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var videoToDelete by remember { mutableStateOf<Video?>(null) }

    var list by remember { mutableStateOf(playlistVideo) }

    val state = rememberReorderableLazyListState(onMove = { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }, onDragEnd = { _, _ ->
        list.forEachIndexed { index, video ->
            updateOrderPlaylistVideo(video.id, index)
        }
    })

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = state.listState,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .reorderable(state),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = list, key = { it.id }) { item ->
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

                var showDeleteBackground by remember { mutableStateOf(true) }

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false, // Отключаем свайп вправо
                    backgroundContent = {
                        DismissBackground(showDeleteBackground)
                    },
                    content = {
                        ReorderableItem(state = state, key = item.id) { isDragging ->
                            showDeleteBackground = !isDragging
                            VideoItem(
                                imageUrl = item.imageUrl ?: "",
                                title = item.name,
                                showIconDrag = true,
                                reorderState = state
                            )
                        }
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
                    list = list.toMutableList().apply { remove(video) }
                    deleteVideoFromPlaylist(video)
                }
                showDialog = false
                videoToDelete = null
            }
        )
    }
}

@Composable
private fun DismissBackground(visible: Boolean) {
    var show by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(500)
            show = true
        } else {
            show = false
        }
    }
    if (show) {
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
            playlistVideo = listOf(
                Video(
                    name = "Разминка перед упражнениями на отдельную группу мыщц",
                    url = "",
                    id = 0.0,
                    description = "321"
                ),
                Video(name = "\"Название видео\"", url = "", id = 0.0, description = "321"),
                Video(name = "\"Название видео\"", url = "", id = 0.0, description = "321")
            ),
            navigateToVideoPlayerScreen = {},
            navigateBackToIntroduction = {},
            navigateToHomeScreen = {},
            changeUser = {},
            accounts = emptyList(),
            activeAccount = Account(name = "", isActive = true),
            deleteVideoFromPlaylist = {},
            updateOrderPlaylistVideo = { _, _ -> }
        )
    }
}