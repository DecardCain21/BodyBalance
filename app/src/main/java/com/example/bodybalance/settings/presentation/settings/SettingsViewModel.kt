package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenState
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeDownloadSettings
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeVisibilitySingOutDialog
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ClearCache
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.SingOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
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

    fun handleEvent(event: SettingsScreenUiEvent) {
        when (event) {
            is ChangeDownloadSettings -> changeDownloadSettings(event.flag)
            is ChangeVisibilitySingOutDialog -> changeVisibilitySingOutDialog(event.flag)
            is ClearCache -> clearCache()
            is SingOut -> signOut()
        }
    }

    private fun changeVisibilitySingOutDialog(flag: Boolean) {
        _uiState.update { it.copy(showLogoutDialog = flag) }
    }

    private fun signOut() {
        viewModelScope.launch {
            logOutOfAccountUseCase()
            _uiState.update { it.copy(navigateToHome = true) }
        }
    }

    private fun clearCache() {
        clearCacheUseCase()
        getCacheSize()
    }

    private fun changeDownloadSettings(flag: Boolean) {
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