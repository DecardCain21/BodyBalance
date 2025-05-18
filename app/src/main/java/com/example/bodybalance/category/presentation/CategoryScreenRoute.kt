package com.example.bodybalance.category.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.category.presentation.state.CategoryScreenUiEvent

@Composable
fun CategoryScreenRoute(
    modifier: Modifier = Modifier,
    navigateToVideoPlayerScreen: (String) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoryScreen(
        modifier = modifier,
        uiState = uiState,
        navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
        navigateToSettingsScreen = navigateToSettingsScreen,
        navigateBackToIntroduction = navigateBackToIntroduction,
        navigateToHomeScreen = navigateToHomeScreen,
        changeUser = { viewModel.handleEvent(CategoryScreenUiEvent.ChangeUser(it)) },
        deleteVideoFromPlaylist = { viewModel.handleEvent(CategoryScreenUiEvent.DeleteVideo(it)) }
    )
}