package com.example.bodybalance.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.bodybalance.ui.theme.Black
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetSystemBarsColor() {
    val systemUiController = rememberSystemUiController()
    val color = Black  // Фиолетовый цвет

    SideEffect {
        // Устанавливаем цвет статус-бара и навигационной панели
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = false  // Светлые иконки на статус-баре
        )
    }
}

// rememberSystemUiController() — контроллер для управления системными панелями.
// setSystemBarsColor — задаёт цвет статус-бара и навигационной панели.
// darkIcons = false — светлые иконки (подходит для тёмного фона).