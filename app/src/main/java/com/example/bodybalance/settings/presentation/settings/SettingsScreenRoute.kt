package com.example.bodybalance.settings.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeDownloadSettings
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeVisibilitySingOutDialog
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ClearCache
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.SingOut

@Composable
internal fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // необходимо, чтобы диалоговое окно скрывалось перед навигацией
    if (uiState.navigateToHome) {
        LaunchedEffect(Unit) {
            navigateToHomeScreen()
        }
    }

    SettingsScreen(
        modifier = modifier,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        navigateToAboutAppScreen = navigateToAboutAppScreen,
        cacheSize = uiState.cacheSize,
        downloadOnlyWifi = uiState.downloadOnlyWifi,
        clearCache = { viewModel.handleEvent(ClearCache) },
        changeDownloadSettings = { viewModel.handleEvent(ChangeDownloadSettings(it)) },
        signOut = { viewModel.handleEvent(SingOut) },
        showLogoutDialog = uiState.showLogoutDialog,
        changeVisibilitySingOutDialog = { viewModel.handleEvent(ChangeVisibilitySingOutDialog(it)) }
    )
}