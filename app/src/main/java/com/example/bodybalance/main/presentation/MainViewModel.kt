package com.example.bodybalance.main.presentation

import androidx.lifecycle.ViewModel
import com.example.bodybalance.main.domain.usecase.CheckAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkAuthUseCase: CheckAuthUseCase,
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    init {
        _isAuthenticated.value = checkAuthUseCase()
    }
}