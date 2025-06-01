package com.example.bodybalance.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.domain.usecase.api.FollowLinkUseCase
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.SnackbarEventParams
import com.example.bodybalance.home.domain.usecase.CheckLoginUseCase
import com.example.bodybalance.home.presentation.state.HomeScreenState
import com.example.bodybalance.home.presentation.state.HomeScreenUiEvent
import com.example.bodybalance.home.presentation.state.SupportTextHome
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
internal class HomeViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val followLinkUseCase: FollowLinkUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _snackbarEvent = MutableSharedFlow<SnackbarEventParams>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

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
                _uiState.update { it.copy(isLoading = true) }
                checkLoginUseCase(uiState.value.inputValue)
                    .onSuccess { _navigationEvent.emit(Unit) }
                    .onFailure { error ->
                        _uiState.value = uiState.value.copy(
                            inputError = true,
                            supportText = SupportTextHome.INVALID_LOGIN,
                            isLoading = false
                        )

                        when (error) {
                            is NetworkError.NoInternet -> {
                                _snackbarEvent.emit(
                                    SnackbarEventParams(
                                        message = "Нет интернета",
                                        actionLabel = "" // todo: нужна ли эта кнопка ?
                                    )
                                )
                            }

                            else -> {
                                _snackbarEvent.emit(
                                    SnackbarEventParams("Что-то не так, попробуйте ещё раз")
                                )
                            }
                        }
                    }
            }
        }
    }

    // todo: заменить ссылку
    private fun requestLogin() {
        followLinkUseCase("")
    }

    private fun clearAll() {
        _uiState.value = uiState.value.copy(inputValue = "")
    }
}