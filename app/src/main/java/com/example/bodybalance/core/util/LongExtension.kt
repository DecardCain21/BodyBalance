package com.example.bodybalance.core.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
public fun Long.convertToFileSize(): String {
    val kb = this / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0

    return when {
        gb >= 1 -> String.format("%.2f GB", gb)
        mb >= 1 -> String.format("%.2f MB", mb)
        kb >= 1 -> String.format("%.2f KB", kb)
        else -> "$this MB"
    }
}