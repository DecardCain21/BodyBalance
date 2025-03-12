package com.example.bodybalance.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    fun handleEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.InputLogin -> inputLoginManagement(event.text)
            is HomeScreenUiEvent.Enter -> onLoginAttempt()
            is HomeScreenUiEvent.GetLogin -> requestLogin()
            is HomeScreenUiEvent.ClearAll -> clearAll()
        }
    }

    private fun inputLoginManagement(text: String) {
        var isError = false

        val supportText = when {
            text.isEmpty() -> SupportTextHome.EMPTY
            text.length < 3 -> {
                isError = true
                SupportTextHome.LOGIN_MIN_LENGTH
            }

            !text.matches(Regex("^[a-zA-Z0-9]+\$")) -> {
                isError = true
                SupportTextHome.LOGIN_REQUIREMENTS
            }

            else -> SupportTextHome.EMPTY
        }
        _uiState.value =
            uiState.value.copy(inputValue = text, inputError = isError, supportText = supportText)
    }

    private fun onLoginAttempt() {
        if (uiState.value.inputValue.isEmpty()) {
            _uiState.value =
                uiState.value.copy(inputError = true, supportText = SupportTextHome.ENTER_LOGIN)
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(Unit)
            }
        }
    }

    private fun requestLogin() { }

    private fun clearAll() {
        _uiState.value = uiState.value.copy(inputValue = "")
    }
}