package com.example.bodybalance.settings.domain.usecase

internal interface SettingsToolsUseCase {

    fun setWifiFlag(flag: Boolean)

    fun getWifiFlag(): Boolean
}