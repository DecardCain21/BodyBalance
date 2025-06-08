package com.example.bodybalance.category.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.category.presentation.state.CategoryScreenState
import com.example.bodybalance.category.presentation.state.CategoryScreenUiEvent
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId

@Composable
internal fun CategoryScreenRoute(
    modifier: Modifier = Modifier,
    navigateToVideoPlayerScreen: (VideoPlayerNavigateScreenId, Int) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    val accountState = when (currentState.accounts) {
        is CategoryScreenState.AccountsState.Content -> currentState.accounts.accountList
        is CategoryScreenState.AccountsState.Empty -> emptyList()
    }

    CategoryScreen(
        modifier = modifier,
        playlistState = currentState.playlistVideo,
        downloadedState = currentState.downloadedState,
        activeAccount = uiState.activeAccount,
        accounts = accountState,
        categoryState = currentState.category,
        navigateToVideoPlayerScreen = { routeId, itemId ->
            navigateToVideoPlayerScreen(routeId, itemId)
        },
        navigateToSettingsScreen = navigateToSettingsScreen,
        navigateBackToIntroduction = navigateBackToIntroduction,
        navigateToHomeScreen = navigateToHomeScreen,
        changeUser = { viewModel.handleEvent(CategoryScreenUiEvent.ChangeUser(it)) },
        deleteVideoFromPlaylist = { viewModel.handleEvent(CategoryScreenUiEvent.DeleteVideo(it)) },
        updateOrderPlaylistVideo = { id, order ->
            viewModel.handleEvent(
                CategoryScreenUiEvent.UpdateOrderPlaylistVideo(
                    id = id, order = order
                )
            )
        },
        deleteSavedVideoFrom = { viewModel.handleEvent(CategoryScreenUiEvent.DeleteSavedVideo(it)) },
        updateOrderSavedVideo = { id, order ->
            viewModel.handleEvent(
                CategoryScreenUiEvent.UpdateOrderSavedVideo(id = id, order = order)
            )
        }
    )
}