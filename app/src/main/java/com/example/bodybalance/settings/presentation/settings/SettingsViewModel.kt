package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutOfAccountUseCase: LogOutOfAccountUseCase
): ViewModel() {

    fun signOut() = logOutOfAccountUseCase()
}