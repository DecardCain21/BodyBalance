package com.example.bodybalance.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    // Основной цвет приложения
    // (Обычно используется для главных элементов интерфейса, таких как AppBar, кнопки и т. д.)
    primary = White,

    // Вспомогательный цвет
    // (Используется для менее важных элементов интерфейса, например, индикаторов)
    secondary = LightGrey,

    // Часто применяется для специальных акцентов, выделений или поддерживающих декоративных элементов
    tertiary = Color.White,

    // Цвет фона приложения
    background = Black,

    // Применяется для фона отдельных компонентов, таких как карточки (Card),
    // модальные окна и т. д.
    // Отличается от background, поскольку представляет фоновую поверхность "над" основным фоном.
    surface = Grey,

    //
    outline = OutlineLightGrey,

    //
    onSurfaceVariant = LightGrey,

    // Цвет текста и иконок, которые отображаются поверх элемента с primary цветом
    onPrimary = Color.Black,

    //
    surfaceContainerLow = Color.White,

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
public fun BodyBalanceTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}