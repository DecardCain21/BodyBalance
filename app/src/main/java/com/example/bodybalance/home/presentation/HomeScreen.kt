package com.example.bodybalance.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.CustomTextField
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp, bottom = 30.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            with(currentState) {
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = inputValue,
                    isError = inputError,
                    supportingText = supportText.message,
                    onValueChange = { viewModel.handleEvent(HomeScreenUiEvent.InputLogin(it)) },
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                text = stringResource(R.string.sing_in),
                onClick = { onSignInClick() }
            )
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                text = stringResource(R.string.get_login),
                buttonColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.primary,
                onClick = { onSignInClick() }
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