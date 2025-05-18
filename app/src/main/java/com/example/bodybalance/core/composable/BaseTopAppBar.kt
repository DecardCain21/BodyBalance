package com.example.bodybalance.core.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.NavigateBefore,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            BaseNavigationIcon(
                navigateBack = navigateBack,
                navigationIcon = navigationIcon
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun BaseNavigationIcon(
    navigateBack: () -> Unit,
    navigationIcon: ImageVector,
    modifier: Modifier = Modifier
) {
    var isBackClickable by remember { mutableStateOf(true) }

    IconButton(
        modifier = modifier,
        onClick = {
            isBackClickable = false
            navigateBack()
        },
        enabled = isBackClickable
    ) {
        Icon(
            imageVector = navigationIcon,
            contentDescription = "Button back",
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun BaseTopAppBarPreview() {
    BodyBalanceTheme {
        BaseTopAppBar(navigateBack = {}, title = "Категории")
    }
}