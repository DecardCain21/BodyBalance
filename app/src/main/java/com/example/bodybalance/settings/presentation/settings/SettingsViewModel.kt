package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutOfAccountUseCase: LogOutOfAccountUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            logOutOfAccountUseCase()
        }
    }

    fun clearCache() {
        clearCacheUseCase()
    }
}