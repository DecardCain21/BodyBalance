package com.example.bodybalance.core.domain.api

interface SettingsToolsRepository {

    fun getDownloadWifiFlag(): Boolean

    fun setDownloadWifiFlag(flag: Boolean)
}