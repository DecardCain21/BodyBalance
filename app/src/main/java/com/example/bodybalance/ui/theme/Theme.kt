package com.example.bodybalance.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    // Основной цвет приложения
    // (Обычно используется для главных элементов интерфейса, таких как AppBar, кнопки и т. д.)
    primary = Blue,

    // Вспомогательный цвет
    // (Используется для менее важных элементов интерфейса, например, индикаторов)
    secondary = Black,

    // Часто применяется для специальных акцентов, выделений или поддерживающих декоративных элементов
    tertiary = Color.White,

    // Цвет фона приложения
    background = Black,

    // Применяется для фона отдельных компонентов, таких как карточки (Card),
    // модальные окна и т. д.
    // Отличается от background, поскольку представляет фоновую поверхность "над" основным фоном.
    surface = Color.White,

    // Цвет текста и иконок, которые отображаются поверх элемента с primary цветом
    onPrimary = Color.White,

    // Цвет текста и иконок поверх secondary
    onSecondary = Grey,

    // Цвет текста и иконок поверх tertiary
    onTertiary = Color.White,

    // Цвет текста и иконок поверх background
    // (Используется для текста, отображаемого на основном фоне приложения)
    onBackground = Color.White,

    // Цвет текста и иконок поверх surface
    // (Применяется для текста на карточках, диалоговых окнах и других подобных поверхностях)
    onSurface = Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = Black,
    tertiary = Black,
    background = Color.White,
    surface = LightGrey,
    onPrimary = LightGrey,
    onSecondary = Grey,
    onTertiary = Color.White,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun BodyBalanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}