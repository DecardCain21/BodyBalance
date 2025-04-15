package com.example.bodybalance.core.composable.snackbar

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { data ->
            Snackbar(
                action = {
                    data.visuals.actionLabel?.let { label ->
                        TextButton(onClick = { data.performAction() }) {
                            Text(text = label) // todo: заменить цвет
                        }
                    }
                }
            ) {
                Text(text = data.visuals.message)
            }
        }
    )
}

@Preview(backgroundColor = 0xFF141218, showBackground = true)
@Composable
private fun SnackBarPreview() {
    Snackbar(
        action = {
            TextButton(onClick = { }) {
                Text(text = "label")
            }
        }
    ) {
        Text(text = "data.visuals.message")
    }
}
