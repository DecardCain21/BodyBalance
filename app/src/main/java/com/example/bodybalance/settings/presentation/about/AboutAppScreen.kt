package com.example.bodybalance.settings.presentation.about

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.BuildConfig
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BaseTopAppBar
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.BottomSheetTextBlack
import com.example.bodybalance.ui.theme.BottomSheetTextGrey

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun AboutAppScreen(
    modifier: Modifier = Modifier,
    navigateBackToSettings: () -> Unit = {},
    openContributorLink: (String) -> Unit = {},
    shareApp: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        BaseTopAppBar(
            modifier = Modifier.padding(bottom = 16.dp),
            navigateBack = navigateBackToSettings,
            title = stringResource(R.string.about_app)
        )

        VersionRow()

        AboutRow(modifier = Modifier.clickable { shareApp() })

        DevelopersRow(onClick = { showBottomSheet = true })

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
                    ContributorsItem(
                        modifier = Modifier.combinedClickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { openContributorLink(Contributors.DEV_NIKITA.url) }
                        ),
                        fieldOfWork = stringResource(R.string.develop),
                        information = "Имя ссылка/контакт"
                    )
                    ContributorsItem(
                        modifier = Modifier.combinedClickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { openContributorLink(Contributors.DEV_MARAT.url) }
                        ),
                        fieldOfWork = stringResource(R.string.develop),
                        information = "Имя ссылка/контакт"
                    )
                    ContributorsItem(
                        modifier = Modifier.combinedClickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { openContributorLink(Contributors.DESIGN_ANASTASIA.url) }
                        ),
                        fieldOfWork = stringResource(R.string.interface_design),
                        information = "Имя ссылка/контакт"
                    )
                }
            }
        }
    }
}

@Composable
private fun VersionRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.vertion),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = BuildConfig.VERSION_NAME,
            fontSize = 11.sp,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun AboutRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.tell_about_body_balance),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Image(
            imageVector = Icons.AutoMirrored.Filled.NavigateNext,
            contentDescription = stringResource(R.string.download_wi_fi_only),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DevelopersRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .combinedClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.developers),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Image(
            imageVector = Icons.AutoMirrored.Filled.NavigateNext,
            contentDescription = stringResource(R.string.download_wi_fi_only),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}

@Composable
private fun ContributorsItem(
    modifier: Modifier = Modifier,
    fieldOfWork: String,
    information: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = fieldOfWork,
            fontSize = 12.sp,
            color = BottomSheetTextGrey
        )
        Text(
            text = information,
            fontSize = 16.sp,
            color = BottomSheetTextBlack
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF141218
)
@Composable
private fun PreviewAboutAppScreen() {
    BodyBalanceTheme {
        AboutAppScreen()
    }
}

