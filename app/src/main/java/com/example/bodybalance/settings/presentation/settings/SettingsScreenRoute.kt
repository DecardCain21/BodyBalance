package com.example.bodybalance.settings.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        navigateToAboutAppScreen = navigateToAboutAppScreen,
        navigateToHomeScreen = navigateToHomeScreen,
        cacheSize = uiState.cacheSize,
        downloadOnlyWifi = uiState.downloadOnlyWifi,
        clearCache = { viewModel.clearCache() },
        changeDownloadSettings = { viewModel.changeDownloadSettings(it) },
        signOut = { viewModel.signOut() }
    )
}