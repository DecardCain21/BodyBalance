package com.example.bodybalance.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

public val Int.nonScaledSp: TextUnit
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp