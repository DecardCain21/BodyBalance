package com.example.bodybalance.category.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    CategoryScreen(
        modifier = modifier,
        uiState = uiState,
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
        }
    )
}