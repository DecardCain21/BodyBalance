package com.example.bodybalance.core.util

public data class SnackbarEventParams(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
)