package com.example.bodybalance.home.presentation.state

internal data class HomeScreenState(
    val inputError: Boolean = false,
    val inputValue: String = "",
    val supportText: SupportTextHome = SupportTextHome.EMPTY,
    val isLoading: Boolean = false
)

internal enum class SupportTextHome(val message: String) {
    INVALID_LOGIN("Неверный логин"),
    ENTER_LOGIN("Введите логин"),
    LOGIN_REQUIREMENTS("Используйте только буквы и цифры"),
    LOGIN_MIN_LENGTH("Логин должен быть не короче 3 символов"),
    EMPTY("")
}