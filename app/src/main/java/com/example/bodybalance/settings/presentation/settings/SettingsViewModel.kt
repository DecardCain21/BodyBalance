package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutOfAccountUseCase: LogOutOfAccountUseCase,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val getFilesCacheSizeUseCase: GetFilesCacheSizeUseCase,
    private val settingsToolsUseCase: SettingsToolsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsScreenState())
    val uiState: StateFlow<SettingsScreenState> = _uiState.asStateFlow()

    init {
        getCacheSize()
        getDownloadWifiFlag()
    }

    fun signOut() {
        viewModelScope.launch {
            logOutOfAccountUseCase()
        }
    }

    fun clearCache() {
        clearCacheUseCase()
        getCacheSize()
    }

    fun changeDownloadSettings(flag: Boolean) {
        settingsToolsUseCase.setWifiFlag(flag)
        getDownloadWifiFlag()
    }

    private fun getCacheSize() {
        _uiState.update { it.copy(cacheSize = getFilesCacheSizeUseCase()) }
    }

    private fun getDownloadWifiFlag() {
        _uiState.update { it.copy(downloadOnlyWifi = settingsToolsUseCase.getWifiFlag()) }
    }
}