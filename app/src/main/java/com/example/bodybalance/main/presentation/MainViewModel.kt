package com.example.bodybalance.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.main.domain.usecase.CheckAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    // todo: из-за того что сейчас checkAuthUseCase() suspent функция,
    //  Home экран успевает октрыться, норм ли ?
    init {
        viewModelScope.launch {
            _isAuthenticated.value = checkAuthUseCase()
        }
    }
}