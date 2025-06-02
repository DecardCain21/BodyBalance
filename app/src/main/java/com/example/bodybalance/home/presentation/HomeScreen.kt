package com.example.bodybalance.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.CustomTextField
import com.example.bodybalance.core.composable.snackbar.CustomSnackbarHost
import com.example.bodybalance.home.presentation.state.HomeScreenState
import com.example.bodybalance.ui.theme.Black
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.OnSurfaceOpacity12
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("OpaqueUnitKey")
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenState,
    snackbarHostState: SnackbarHostState,
    inputLogin: (String) -> Unit,
    clearAll: () -> Unit,
    accountEnter: () -> Unit,
    getLogin: () -> Unit,
    isLoading: Boolean
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
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
                painter = painterResource(id = R.drawable.logo),
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
                    onValueChange = { inputLogin(it) },
                    trailingIcon = {
                        if (isFocused && inputValue.isNotEmpty() || inputError) {
                            LabelIcon(
                                clearAll = { clearAll() },
                                isError = inputError
                            )
                        }
                    }
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                onClick = { accountEnter() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = OnSurfaceOpacity12,
                    disabledContentColor = OnSurfaceOpacity12,
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(top = 18.dp, bottom = 18.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Black
                    )
                }
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = stringResource(R.string.sing_in),
                    fontSize = 14.sp,
                )
            }

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
                GetLoginBlockBottomSheet(
                    sheetState = sheetState,
                    showBottomSheetAction = { showBottomSheet = false },
                    getLogin = { getLogin() }
                )
            }
        }
        CustomSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GetLoginBlockBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    showBottomSheetAction: () -> Unit,
    getLogin: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
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
                getLogin()
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheetAction()
                    }
                }
            }
        )
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
            HomeScreen(
                uiState = HomeScreenState(),
                accountEnter = {},
                snackbarHostState = SnackbarHostState(),
                inputLogin = {},
                clearAll = {},
                getLogin = {},
                isLoading = false
            )
        }
    }
}