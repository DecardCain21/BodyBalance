package com.example.bodybalance.settings.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.BuildConfig
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.util.convertToFileSize
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    downloadOnlyWifi: Boolean,
    cacheSize: Long,
    signOut: () -> Unit,
    clearCache: () -> Unit,
    changeDownloadSettings: (Boolean) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BaseTopAppBar(
                navigateBack = navigateBackToPlaylistScreen,
                title = stringResource(R.string.settings),
                navigationIcon = Icons.Default.Close
            )
        },
        snackbarHost = {}
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            ClearCacheRow(cacheSize) { clearCache() }
            DownloadOnlyWifiRow(
                checked = downloadOnlyWifi,
                onCheckedChange = changeDownloadSettings
            )
            AboutAppRow(onClick = navigateToAboutAppScreen)

            Spacer(modifier = Modifier.weight(1f))

            BasicButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.exit),
                buttonColor = Color.Transparent,
                enabledTextColor = MaterialTheme.colorScheme.primary,
                onClick = { showDialog = true }
            )
        }

        LogoutDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = {
                signOut()
                navigateToHomeScreen()
                showDialog = false
            }
        )
    }
}

@Composable
private fun ClearCacheRow(
    cacheSize: Long,
    onClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable { onClear() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Image(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .size(24.dp)
                .align(Alignment.Top),
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.clear_cashe),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.clear_cashe),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
            )
            Text(
                text = stringResource(R.string.clear_cashe_description),
                fontSize = 12.sp,
                color = colorResource(R.color.white),
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.Top),
            text = cacheSize.convertToFileSize(),
            fontSize = 11.sp,
            color = colorResource(R.color.white),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))
    }
}

@Composable
private fun DownloadOnlyWifiRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp),
            imageVector = Icons.Default.Download,
            contentDescription = stringResource(R.string.download_wi_fi_only),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.download_wi_fi_only),
            fontSize = 16.sp,
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkedTrackColor = Color.White,
                uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                checkedBorderColor = Color.White,
                uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
fun AboutAppRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.about_app),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.about_app),
            fontSize = 16.sp,
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.version_app, BuildConfig.VERSION_NAME),
            fontSize = 16.sp,
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))
    }
}

@Composable
private fun LogoutDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    modifier = Modifier.padding(end = 30.dp),
                    text = stringResource(R.string.logout_of_account),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(text = stringResource(R.string.logout))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.cansel))
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    BodyBalanceTheme {
        SettingsScreen(
            navigateToAboutAppScreen = {},
            navigateBackToPlaylistScreen = {},
            navigateToHomeScreen = {},
            cacheSize = 100L,
            downloadOnlyWifi = true,
            clearCache = {},
            changeDownloadSettings = {},
            signOut = {}
        )
    }
}