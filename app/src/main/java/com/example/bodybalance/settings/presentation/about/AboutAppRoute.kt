package com.example.bodybalance.settings.presentation.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun AboutAppRoute(
    modifier: Modifier = Modifier,
    navigateBackToSettings: () -> Unit = {},
    viewMode: AboutAppViewModel = hiltViewModel()
) {

    // todo: вынести стрингу в ресурсы
    AboutAppScreen(
        modifier = modifier,
        navigateBackToSettings = navigateBackToSettings,
        openContributorLink = {
            viewMode.handleEvent(
                AboutAppScreenUiEvent.OpenContributorLink(it)
            )
        },
        shareApp = {
            viewMode.handleEvent(
                AboutAppScreenUiEvent.ShareTheApp(
                    title = "Поделиться приложением", text = "Тест"
                )
            )
        }
    )
}