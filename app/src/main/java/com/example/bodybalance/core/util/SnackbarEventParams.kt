package com.example.bodybalance.core.util

import androidx.compose.material3.SnackbarDuration

public data class SnackbarEventParams(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val withDissmiss: Boolean = false
)