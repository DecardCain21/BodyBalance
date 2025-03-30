package com.example.bodybalance.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.CustomTextField
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("OpaqueUnitKey")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit = {},
    navigateToIntroductionScreen: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val navEvent by viewModel.navigationEvent.collectAsState(initial = null)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
        }
    }

    LaunchedEffect(navEvent) {
        navEvent?.let {
            navigateToIntroductionScreen()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 144.dp, bottom = 60.dp),
                painter = painterResource(id = R.drawable.logo_),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            with(uiState) {
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = inputValue,
                    isError = inputError,
                    label = stringResource(id = R.string.login),
                    supportingText = supportText.message,
                    interactionSource = interactionSource,
                    onValueChange = { viewModel.handleEvent(HomeScreenUiEvent.InputLogin(it)) },
                    trailingIcon = {
                        if (isFocused && inputValue.isNotEmpty() || inputError) {
                            LabelIcon(
                                clearAll = { viewModel.handleEvent(HomeScreenUiEvent.ClearAll) },
                                isError = inputError
                            )
                        }
                    }
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                text = stringResource(R.string.sing_in),
                onClick = { viewModel.handleEvent(HomeScreenUiEvent.Enter) }
            )
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                text = stringResource(R.string.get_login),
                buttonColor = Color.Transparent,
                enabledTextColor = MaterialTheme.colorScheme.primary,
                onClick = { showBottomSheet = true }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                dragHandle = {
                    BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outline)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Получите логин после приёма врача",
                        fontSize = 22.sp,
                        letterSpacing = 0.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp, bottom = 14.dp),
                        text = "Body Balance— это программа спортивной реабилитации в формате ежедневных видео",
                        fontSize = 14.sp,
                        letterSpacing = 0.25.sp,
                    )
                    Text(
                        text = "Чтобы получить доступ к программе, запишитесь на приём к врачу",
                        fontSize = 14.sp,
                        letterSpacing = 0.25.sp,
                    )
                    BasicButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        text = "Записаться на приём",
                        buttonColor = MaterialTheme.colorScheme.onPrimary,
                        enabledTextColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        })
                }
            }
        }
    }
}

@Composable
private fun LabelIcon(
    isError: Boolean,
    clearAll: () -> Unit,
) {
    if (isError) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = stringResource(R.string.error),
        )
    } else {
        IconButton(onClick = clearAll) {
            Icon(
                imageVector = Icons.Default.HighlightOff,
                contentDescription = stringResource(R.string.clear)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    BodyBalanceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HomeScreen()
        }
    }
}