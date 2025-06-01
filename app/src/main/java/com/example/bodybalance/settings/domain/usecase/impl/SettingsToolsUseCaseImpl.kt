package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import javax.inject.Inject

internal class SettingsToolsUseCaseImpl @Inject constructor(
    private val settingsToolsRepository: SettingsToolsRepository
): SettingsToolsUseCase {

    override fun setWifiFlag(flag: Boolean) {
        settingsToolsRepository.setDownloadWifiFlag(flag)
    }

    override fun getWifiFlag(): Boolean {
        return settingsToolsRepository.getDownloadWifiFlag()
    }
}