package com.example.bodybalance.introduction.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenUiEvent

@Composable
fun IntroductionScreenRoute(
    modifier: Modifier = Modifier,
    navToPlaylist: () -> Unit = {},
    viewModel: IntroductionViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    IntroductionScreen(
        modifier = modifier,
        uiState = uiState,
        inputCodeWord = { viewModel.handleEvent(IntroductionScreenUiEvent.InputLogin(it)) },
        getVideo = { viewModel.getIntro() },
        navToPlaylist = navToPlaylist,
        eventContinue = {viewModel.handleEvent(IntroductionScreenUiEvent.Continue)},
    )
}