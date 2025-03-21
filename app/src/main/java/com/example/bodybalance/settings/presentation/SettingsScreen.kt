package com.example.bodybalance.settings.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Настройки")
            },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )
        // Первая строка: Очистить кэш
        Row(
            modifier = Modifier
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Delete, /*painter = painterResource(id = R.drawable.your_custom_icon)*/
                contentDescription = "Очистить кэш",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Очистить кэш",
                    fontSize = 16.sp,
                    color = colorResource(R.color.white),
                )
                Text(
                    text = "Освободите память устройства. Видео останутся в приложении, и вы сможете скачать их заново",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white),
                )
            }
            //Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "5 Гб",
                fontSize = 11.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.End
            )
        }

        // Вторая строка: Скачивать видео только по Wi-Fi
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Download,
                contentDescription = stringResource(R.string.download_wi_fi_only),
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.download_wi_fi_only),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = false,
                onCheckedChange = {},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black, // Цвет "ползунка" в активном состоянии
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant, // Цвет "ползунка" в неактивном состоянии
                    checkedTrackColor = Color.White, // Цвет фона в активном состоянии
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface, //На макете outline? Цвет фона в неактивном состоянии
                    checkedBorderColor = Color.White, // Цвет обводки в активном состоянии
                    uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant // Цвет обводки в неактивном состоянии
                )
            )
        }

        // Третья строка: О приложении
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(R.string.about_app),
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.about_app),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.version_app),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BasicButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            text = stringResource(R.string.exit),
            buttonColor = Color.Transparent,
            enabledTextColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )


    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF141218
)
@Composable
fun PreviewSettingsScreen() {
    BodyBalanceTheme(dynamicColor = false, darkTheme = true) {
        SettingsScreen()
    }
}