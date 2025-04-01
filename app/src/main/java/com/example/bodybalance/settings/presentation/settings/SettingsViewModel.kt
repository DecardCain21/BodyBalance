package com.example.bodybalance.settings.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutOfAccount: LogOutOfAccount
): ViewModel() {

    fun signOut() = logOutOfAccount()
}