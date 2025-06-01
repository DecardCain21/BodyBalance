package com.example.bodybalance.core.domain.api

public interface SettingsToolsRepository {

    public fun getDownloadWifiFlag(): Boolean

    public fun setDownloadWifiFlag(flag: Boolean)
}