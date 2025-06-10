package com.example.bodybalance.core.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
public fun Long.convertToFileSize(): String {
    require(this >= 0) { "File size cannot be negative" }

    return when {
        this == 0L -> "0 B"
        this < 1024 -> "$this B"
        this < 1024 * 1024 -> "%.1f KB".format(this / 1024.0)
        this < 1024 * 1024 * 1024 -> "%.1f MB".format(this / (1024.0 * 1024.0))
        else -> "%.1f GB".format(this / (1024.0 * 1024.0 * 1024.0))
    }
}