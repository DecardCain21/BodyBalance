package com.example.bodybalance.settings.presentation.settings

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeDownloadSettings
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.CloseDialog
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ClearCache
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.SingOut
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
internal fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    // необходимо, чтобы диалоговое окно скрывалось перед навигацией
    if (uiState.navigateToHome) {
        LaunchedEffect(Unit) {
            navigateToHomeScreen()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { params ->
            snackbarJob?.cancel()
            snackbarJob = launch {
                val result = snackbarHostState.showSnackbar(
                    message = params.message,
                    actionLabel = params.actionLabel,
                    duration = SnackbarDuration.Short
                )

                if (result == SnackbarResult.ActionPerformed) {
                    params.onAction?.invoke()
                }
            }
        }
    }

    SettingsScreen(
        modifier = modifier,
        snackBarHostState = snackbarHostState,
        navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
        navigateToAboutAppScreen = navigateToAboutAppScreen,
        cacheSize = uiState.cacheSize,
        downloadOnlyWifi = uiState.downloadOnlyWifi,
        clearCache = { viewModel.handleEvent(ClearCache) },
        changeDownloadSettings = { viewModel.handleEvent(ChangeDownloadSettings(it)) },
        signOut = { viewModel.handleEvent(SingOut) },
        showLogoutDialog = uiState.showDialog,
        closeDialog = { viewModel.handleEvent(CloseDialog) },
        dialogData = uiState.dialogData
    )
}