package com.example.bodybalance.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier,
    navigateBackToSettings: () -> Unit = {}
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
                Text(
                    modifier = Modifier.padding(vertical = 18.dp),
                    text = stringResource(R.string.about_app)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigateBackToSettings() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )
        // Первая строка: Версия
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Версия",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
            )
            //Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = "0.0.0",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )
        }

        // Вторая строка: Скачивать видео только по Wi-Fi
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Рассказать о Body Balance",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.download_wi_fi_only),
                modifier = Modifier.size(24.dp, 24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }

        // Третья строка: О приложении
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Разработчики",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.download_wi_fi_only),
                modifier = Modifier.size(24.dp, 24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF141218
)
@Composable
fun PreviewAboutAppScreen() {
    BodyBalanceTheme(dynamicColor = false, darkTheme = true) {
        AboutAppScreen()
    }
}