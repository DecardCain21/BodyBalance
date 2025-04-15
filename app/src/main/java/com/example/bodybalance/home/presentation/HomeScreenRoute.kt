package com.example.bodybalance.home.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.home.presentation.HomeScreenUiEvent.ClearAll
import com.example.bodybalance.home.presentation.HomeScreenUiEvent.Enter
import com.example.bodybalance.home.presentation.HomeScreenUiEvent.GetLogin
import com.example.bodybalance.home.presentation.HomeScreenUiEvent.InputLogin
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToIntroductionScreen: () -> Unit = {},
) {

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navEvent by viewModel.navigationEvent.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { params ->
            snackbarJob?.cancel()
            snackbarJob = launch {
                snackbarHostState.showSnackbar(
                    message = params.message,
                    actionLabel = params.actionLabel,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        navEvent = navEvent,
        snackbarHostState = snackbarHostState,
        navigateToIntroductionScreen = { navigateToIntroductionScreen() },
        inputLogin = { viewModel.handleEvent(InputLogin(it)) },
        clearAll = { viewModel.handleEvent(ClearAll) },
        accountEnter = { viewModel.handleEvent(Enter) },
        getLogin = { viewModel.handleEvent(GetLogin) }
    )
}