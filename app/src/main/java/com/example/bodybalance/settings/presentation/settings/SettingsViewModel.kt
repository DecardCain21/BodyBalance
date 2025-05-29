package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
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
    private val getFilesCacheSizeUseCase: GetFilesCacheSizeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsScreenState())
    val uiState: StateFlow<SettingsScreenState> = _uiState.asStateFlow()

    init {
        getCacheSize()
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

    private fun getCacheSize() {
        _uiState.update { it.copy(cacheSize = getFilesCacheSizeUseCase()) }
    }
}