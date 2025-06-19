package com.example.bodybalance.core.composable.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
public fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { data -> CustomSnackbar(data) }
    )
}

@Composable
private fun CustomSnackbar(data: SnackbarData) {
    Snackbar(
        action = {
            data.visuals.actionLabel?.let { label ->
                Row {
                    TextButton(onClick = { data.performAction() }) {
                        Text(text = label, color = MaterialTheme.colorScheme.onSurface)
                    }
                    if (data.visuals.withDismissAction)
                        IconButton(onClick = { data.dismiss() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Закрыть",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                }
            }
        }
    ) {
        Text(text = data.visuals.message)
    }
}

@Preview(backgroundColor = 0xFF141218, showBackground = true)
@Composable
private fun SnackBarPreview() {
    BodyBalanceTheme {
        val snackbarData = object : SnackbarData {
            override val visuals: SnackbarVisuals = object : SnackbarVisuals {
                override val message: String = "Пример сообщения"
                override val actionLabel: String = "Отмена"
                override val withDismissAction: Boolean = true
                override val duration: SnackbarDuration = SnackbarDuration.Short
            }

            override fun dismiss() {}
            override fun performAction() {
            }
        }
        CustomSnackbar(snackbarData)
    }
}
