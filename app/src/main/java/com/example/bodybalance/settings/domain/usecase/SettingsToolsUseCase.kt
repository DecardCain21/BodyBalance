package com.example.bodybalance.settings.domain.usecase

interface SettingsToolsUseCase {

    fun setWifiFlag(flag: Boolean)

    fun getWifiFlag(): Boolean
}