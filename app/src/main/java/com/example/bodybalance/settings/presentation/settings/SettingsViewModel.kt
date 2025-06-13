package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.util.SnackbarEventParams
import com.example.bodybalance.core.util.convertToFileSize
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import com.example.bodybalance.settings.domain.usecase.SettingsToolsUseCase
import com.example.bodybalance.settings.presentation.settings.state.DialogData
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenState
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ChangeDownloadSettings
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.ClearCache
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.CloseDialog
import com.example.bodybalance.settings.presentation.settings.state.SettingsScreenUiEvent.SingOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val _snackBarEvent = MutableSharedFlow<SnackbarEventParams>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    init {
        getCacheSize()
        getDownloadWifiFlag()
    }

    fun handleEvent(event: SettingsScreenUiEvent) {
        when (event) {
            is ChangeDownloadSettings -> changeDownloadSettings(event.flag)
            is CloseDialog -> closeDialog()
            is ClearCache -> clearCache()
            is SingOut -> signOut()
        }
    }


    private fun closeDialog() {
        _uiState.update { it.copy(showDialog = false) }
    }

    private fun signOut() {
        _uiState.update { state ->
            state.copy(
                showDialog = true,
                dialogData = DialogData(
                    title = "Выйти из аккаунта?",
                    action = "Выйти",
                    onAction = {
                        viewModelScope.launch {
                            logOutOfAccountUseCase()
                            _uiState.update { it.copy(navigateToHome = true) }
                        }
                    }
                )
            )
        }
    }

    private fun clearCache() {
        _uiState.update { state ->
            state.copy(
                showDialog = true,
                dialogData = DialogData(
                    title = "Очистить кэш?",
                    action = "Очистить",
                    onAction = {
                        viewModelScope.launch {
                            _snackBarEvent.emit(
                                SnackbarEventParams(
                                    message = uiState
                                        .value
                                        .cacheSize
                                        .convertToFileSize() + ACTION_CLEAN_CACHE
                                )
                            )
                            clearCacheUseCase()
                            getCacheSize()
                        }
                    }
                )
            )
        }
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

    companion object {
        private const val ACTION_CLEAN_CACHE = " на устройстве освободилось"
    }
}