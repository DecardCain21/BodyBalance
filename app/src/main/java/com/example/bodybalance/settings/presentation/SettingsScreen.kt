package com.example.bodybalance.settings.presentation

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(R.string.settings))
            },
            navigationIcon = {
                IconButton(onClick = { navigateBackToPlaylistScreen() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                text = "5 Гб",
                fontSize = 11.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.End
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                checked = false,
                onCheckedChange = {},
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigateToAboutAppScreen() },
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                text = stringResource(R.string.version_app),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BasicButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.exit),
            buttonColor = Color.Transparent,
            enabledTextColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF141218
)
@Composable
fun PreviewSettingsScreen() {
    BodyBalanceTheme(dynamicColor = false, darkTheme = true) {
        SettingsScreen(navigateToAboutAppScreen = {}, navigateBackToPlaylistScreen = {})
    }
}