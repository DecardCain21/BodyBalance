package com.example.bodybalance.category.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.category.presentation.state.CategoryScreenState.CategoryState
import com.example.bodybalance.category.presentation.state.CategoryScreenState.DownloadedState
import com.example.bodybalance.category.presentation.state.CategoryScreenState.PlaylistState
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.items.ExerciseItem
import com.example.bodybalance.core.composable.items.VideoItem
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.TabRowDividerColor
import com.example.bodybalance.ui.theme.White
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
internal fun CategoryScreen(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    activeAccount: Account,
    categoryState: CategoryState,
    playlistState: PlaylistState,
    downloadedState: DownloadedState,
    navigateBackToIntroduction: () -> Unit,
    navigateToVideoPlayerScreen: (routeId: VideoPlayerNavigateScreenId, itemId: Int) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    changeUser: (Account) -> Unit,
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Int, order: Int) -> Unit,
    deleteSavedVideoFrom: (Video) -> Unit,
    updateOrderSavedVideo: (id: Int, order: Int) -> Unit,
    updateExercise: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                accounts = accounts,
                activeAccount = activeAccount,
                onAccountSelected = changeUser,
                navigateToSettingsScreen = navigateToSettingsScreen,
                navigateBackToIntroduction = navigateBackToIntroduction,
                navigateToHomeScreen = navigateToHomeScreen,
            )
        }
    ) { paddingValue ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            CategoryPages(
                categoryState = categoryState,
                playlistState = playlistState,
                savedVideos = downloadedState,
                navigateToVideoScreenFromDownloaded = {
                    navigateToVideoPlayerScreen(VideoPlayerNavigateScreenId.DOWNLOADED, it)
                },
                navigateToVideoPlayerScreen = {
                    navigateToVideoPlayerScreen(VideoPlayerNavigateScreenId.CATEGORY, it)
                },
                navigateToVideoPlayerScreenFromPlaylist = {
                    navigateToVideoPlayerScreen(VideoPlayerNavigateScreenId.PLAYLIST, it)
                },
                deleteVideoFromPlaylist = { deleteVideoFromPlaylist(it) },
                updateOrderPlaylistVideo = updateOrderPlaylistVideo,
                deleteSavedVideoFrom = { deleteSavedVideoFrom(it) },
                updateOrderSavedVideo = updateOrderSavedVideo,
                updateExercise = updateExercise
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    accounts: List<Account>,
    activeAccount: Account,
    onAccountSelected: (Account) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    BaseTopAppBar(
        navigateBack = navigateBackToIntroduction,
        actions = {
            IconButton(
                onClick = { showBottomSheet = true }
            ) {
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
                activeAccount = activeAccount,
                onAccountSelected = { onAccountSelected(it) },
                onAddAccountClick = {
                    navigateToHomeScreen()
                    showBottomSheet = false
                },
                sheetState = sheetState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ChangeUserBlock(
    accounts: List<Account>,
    activeAccount: Account,
    onAccountSelected: (Account) -> Unit,
    onAddAccountClick: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxWidth()) {
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
                    selected = activeAccount == account,
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
    categoryState: CategoryState,
    playlistState: PlaylistState,
    savedVideos: DownloadedState,
    updateExercise: () -> Unit,
    navigateToVideoPlayerScreen: (Int) -> Unit, // Int - Id категории
    navigateToVideoPlayerScreenFromPlaylist: (Int) -> Unit, // Int - Id видео
    navigateToVideoScreenFromDownloaded: (Int) -> Unit, // Int - Id видео
    deleteVideoFromPlaylist: (Video) -> Unit,
    updateOrderPlaylistVideo: (id: Int, order: Int) -> Unit,
    deleteSavedVideoFrom: (Video) -> Unit,
    updateOrderSavedVideo: (id: Int, order: Int) -> Unit,
) {
    val tabs = listOf(
        stringResource(R.string.playlist),
        stringResource(R.string.exercises),
        stringResource(R.string.downloaded),
    )
    val pagerState = rememberPagerState(initialPage = 1) { tabs.size }
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
                    .padding(horizontal = 35.dp)
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
                text = {
                    Text(
                        text = title,
                        fontWeight = FontWeight(700),
                        fontSize = 14.nonScaledSp
                    )
                }
            )
        }
    }

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> {
                when (playlistState) {
                    is PlaylistState.Content -> VideoItemsScreen(
                        videoItems = playlistState.playlistVideo,
                        navigateToVideoPlayerScreenFromPlaylist = navigateToVideoPlayerScreenFromPlaylist,
                        deleteVideoFrom = { deleteVideoFromPlaylist(it) },
                        updateOrderVideoItems = updateOrderPlaylistVideo,
                        categoryOn = true,
                        titleDialog = stringResource(R.string.delete_video_from_playlist),
                        subtitleDialog = stringResource(R.string.delete_playlist_video_dialog_message)
                    )

                    is PlaylistState.Empty ->
                        PlaylistEmptyScreen(
                            modifier = Modifier.padding(bottom = 56.dp),
                            stringResource(id = R.string.placeholder_subtext_playlist)
                        )
                }
            }

            1 -> {
                when (categoryState) {
                    is CategoryState.Content -> ExerciseScreen(
                        category = categoryState.categoryList,
                        navigateToVideoPlayerScreen = navigateToVideoPlayerScreen
                    )

                    is CategoryState.Empty ->
                        ExerciseEmptyScreen(
                            modifier = Modifier.padding(bottom = 56.dp),
                            updateExercise = updateExercise
                        )

                    is CategoryState.Loading ->
                        ExerciseLoadingScreen(modifier = Modifier.padding(bottom = 56.dp))
                }
            }

            2 -> {
                when (savedVideos) {
                    is DownloadedState.Content -> VideoItemsScreen(
                        videoItems = savedVideos.downloadedVideo,
                        navigateToVideoPlayerScreenFromPlaylist = navigateToVideoScreenFromDownloaded,
                        deleteVideoFrom = { deleteSavedVideoFrom(it) },
                        updateOrderVideoItems = updateOrderSavedVideo,
                        categoryOn = true,
                        titleDialog = stringResource(R.string.delete_saved_video_dialog_message),
                    )

                    is DownloadedState.Empty ->
                        PlaylistEmptyScreen(
                            modifier = Modifier.padding(bottom = 56.dp),
                            descriptionPlaceholder = stringResource(R.string.placeholder_subtext_savedVideo)
                        )
                }
            }

            else -> Unit
        }
    }
}

@Composable
private fun ExerciseLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ExerciseEmptyScreen(
    modifier: Modifier = Modifier,
    updateExercise: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Нет интернета",
            fontSize = 22.sp,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        BasicButton(text = "Обновить", onClick = { updateExercise() })
    }
}

@Composable
private fun ExerciseScreen(
    category: List<Category>,
    modifier: Modifier = Modifier,
    navigateToVideoPlayerScreen: (Int) -> Unit
) {
    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            state = rememberLazyListState()
        ) {
            items(items = category, key = { it.id }) { item ->
                ExerciseItem(
                    modifier = Modifier.combinedClickable(
                        indication = null,
                        interactionSource = null,
                        onClick = { navigateToVideoPlayerScreen(item.id) }
                    ),
                    title = item.name,
                    imageUrl = item.imageUrl
                )
            }
        }
    }
}

@Composable
private fun PlaylistEmptyScreen(
    modifier: Modifier = Modifier,
    descriptionPlaceholder: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.do_not_have_video),
            fontSize = 22.sp,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = descriptionPlaceholder,
            fontSize = 14.sp,
            color = White,
            textAlign = TextAlign.Center,
            letterSpacing = 0.25.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun VideoItemsScreen(
    videoItems: List<Video>,
    categoryOn: Boolean,
    navigateToVideoPlayerScreenFromPlaylist: (Int) -> Unit,
    deleteVideoFrom: (Video) -> Unit,
    titleDialog: String,
    modifier: Modifier = Modifier,
    subtitleDialog: String? = null,
    updateOrderVideoItems: (id: Int, order: Int) -> Unit = { _, _ -> }
) {
    var showDialog by remember { mutableStateOf(false) }
    var videoToDelete by remember { mutableStateOf<Video?>(null) }

    var list by remember { mutableStateOf(videoItems) }

    LaunchedEffect(videoItems) {
        list = videoItems
    }

    val state = rememberReorderableLazyListState(onMove = { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }, onDragEnd = { _, _ ->
        list.forEachIndexed { index, video ->
            updateOrderVideoItems(video.id, index)
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

                ReorderableItem(
                    state = state,
                    key = item.id
                ) { isDragging ->
                    showDeleteBackground = !isDragging

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false, // Отключаем свайп вправо
                        backgroundContent = { DismissBackground(showDeleteBackground) },
                        content = {
                            VideoItem(
                                modifier = Modifier.clickable {
                                    navigateToVideoPlayerScreenFromPlaylist(item.id)
                                },
                                imageUrl = item.imageUrl,
                                title = item.name,
                                category = if (categoryOn) item.category else "",
                                showIconDrag = true,
                                reorderState = state
                            )
                        }
                    )
                }
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
                    deleteVideoFrom(video)
                }
                showDialog = false
                videoToDelete = null
            },
            titleDialog = titleDialog,
            subtitleDialog = subtitleDialog
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
    titleDialog: String,
    subtitleDialog: String?,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                modifier = Modifier.padding(end = 30.dp),
                text = titleDialog,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = if (subtitleDialog != null) {
            {
                Text(
                    text = subtitleDialog,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)
                )
            }
        } else null,
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

@Preview
@Composable
private fun PreviewPlaylist() {
    BodyBalanceTheme {
        CategoryScreen(
            navigateToSettingsScreen = {},
            navigateToVideoPlayerScreen = { _, _ -> },
            navigateBackToIntroduction = {},
            navigateToHomeScreen = {},
            changeUser = {},
            deleteVideoFromPlaylist = {},
            updateOrderPlaylistVideo = { _, _ -> },
            playlistState = PlaylistState.Empty,
            activeAccount = Account.empty(1),
            accounts = emptyList(),
            categoryState = CategoryState.Empty,
            downloadedState = DownloadedState.Empty,
            deleteSavedVideoFrom = {},
            updateOrderSavedVideo = { _, _ -> },
            updateExercise = {}
        )
    }
}