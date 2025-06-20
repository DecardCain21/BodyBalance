package com.example.bodybalance.home.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.R
import com.example.bodybalance.home.presentation.state.HomeScreenUiEvent.ClearAll
import com.example.bodybalance.home.presentation.state.HomeScreenUiEvent.Enter
import com.example.bodybalance.home.presentation.state.HomeScreenUiEvent.GetLogin
import com.example.bodybalance.home.presentation.state.HomeScreenUiEvent.InputLogin
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("OpaqueUnitKey")
@Composable
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToIntroductionScreen: () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navEvent by viewModel.navigationEvent.collectAsState(initial = null)

    LaunchedEffect(navEvent) {
        navEvent?.let {
            navigateToIntroductionScreen()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { params ->
            snackbarJob?.cancel()
            snackbarJob = launch {
                val result = snackbarHostState.showSnackbar(
                    message = params.message,
                    actionLabel = params.actionLabel,
                    duration = params.duration,
                    withDismissAction = params.withDismiss
                )

                if (result == SnackbarResult.ActionPerformed) {
                    params.onAction?.invoke()
                }
            }
        }
    }
    val link = stringResource(R.string.link_get_login)

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        inputLogin = { viewModel.handleEvent(InputLogin(it)) },
        clearAll = { viewModel.handleEvent(ClearAll) },
        accountEnter = { viewModel.handleEvent(Enter) },
        getLogin = { viewModel.handleEvent(GetLogin(link)) },
        isLoading = uiState.isLoading
    )
}