package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.storage.PreferencesStorage
import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import javax.inject.Inject

class SettingsToolsRepositoryImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
): SettingsToolsRepository {

    override fun getDownloadWifiFlag(): Boolean {
        return preferencesStorage.useOnlyWifi
    }

    override fun setDownloadWifiFlag(flag: Boolean) {
        preferencesStorage.useOnlyWifi = flag
    }
}