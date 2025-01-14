package com.example.bodybalance.home.presenation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.TextField
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.Grey

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit = {},
    onSignInClick: () -> Unit = {}
) {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp, bottom = 30.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = loginText,
                placeHolderText = stringResource(R.string.login),
                onValueChange = { newText -> loginText = newText }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                text = passwordText,
                singleLine = true,
                placeHolderText = stringResource(R.string.password),
                isPasswordField = true,
                onValueChange = { newText -> passwordText = newText }
            )
            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp, end = 14.dp)
                    .clickable { onForgotPasswordClick() },
                color = Grey,
                fontSize = 14.sp
            )
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.sing_in),
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