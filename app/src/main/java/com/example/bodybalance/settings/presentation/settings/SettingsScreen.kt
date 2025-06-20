package com.example.bodybalance.settings.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.BuildConfig
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.snackbar.CustomSnackbarHost
import com.example.bodybalance.core.util.convertToFileSize
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.settings.presentation.settings.state.DialogData
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    downloadOnlyWifi: Boolean,
    cacheSize: Long,
    dialogData: DialogData,
    clearCache: () -> Unit,
    signOut: () -> Unit,
    changeDownloadSettings: (Boolean) -> Unit,
    showLogoutDialog: Boolean,
    closeDialog: () -> Unit
) {
    Scaffold(
        topBar = {
            BaseTopAppBar(
                navigateBack = navigateBackToPlaylistScreen,
                title = stringResource(R.string.settings),
                navigationIcon = Icons.Default.Close
            )
        },
        snackbarHost = {
            CustomSnackbarHost(
                modifier = Modifier.padding(horizontal = 16.dp),
                hostState = snackBarHostState
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            ClearCacheRow(cacheSize = cacheSize) {
                if (cacheSize != 0L) clearCache()
            }
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
                onClick = { signOut() }
            )
        }

        LogoutDialog(
            dialogData = dialogData,
            showDialog = showLogoutDialog,
            onDismiss = { closeDialog() },
            onConfirm = {
                closeDialog()
                dialogData.onAction()
            }
        )
    }
}

@Composable
private fun ClearCacheRow(
    cacheSize: Long,
    onClear: () -> Unit
) {
    Box {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    onClear()
                },
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
                    fontSize = 16.nonScaledSp,
                    color = colorResource(R.color.white),
                )
                Text(
                    text = stringResource(R.string.clear_cashe_description),
                    fontSize = 12.nonScaledSp,
                    color = colorResource(R.color.white),
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.Top),
                text = cacheSize.convertToFileSize(),
                fontSize = 11.nonScaledSp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.white),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
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
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = stringResource(R.string.download_wi_fi_only),
            fontSize = 16.nonScaledSp,
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
private fun AboutAppRow(onClick: () -> Unit) {
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
            fontSize = 16.nonScaledSp,
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.version_app, BuildConfig.VERSION_NAME),
            fontSize = 11.nonScaledSp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))
    }
}

@Composable
private fun LogoutDialog(
    dialogData: DialogData,
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
                    modifier = Modifier.padding(end = 24.dp),
                    text = dialogData.title,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                Text(
                    modifier = Modifier.padding(end = 24.dp),
                    text = stringResource(R.string.dialog_description),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.nonScaledSp,
                    fontWeight = FontWeight(400)
                )
            },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(text = dialogData.action)
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
            snackBarHostState = SnackbarHostState(),
            cacheSize = 100L,
            downloadOnlyWifi = true,
            clearCache = {},
            changeDownloadSettings = {},
            dialogData = DialogData(),
            showLogoutDialog = false,
            closeDialog = {},
            signOut = {}
        )
    }
}