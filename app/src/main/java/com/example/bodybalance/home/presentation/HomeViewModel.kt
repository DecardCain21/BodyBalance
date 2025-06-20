package com.example.bodybalance.home.presentation

import androidx.compose.material3.SnackbarDuration
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
            is HomeScreenUiEvent.GetLogin -> requestLogin(event.link)
            is HomeScreenUiEvent.ClearAll -> clearAll()
        }
    }

    private fun inputLoginManagement(text: String) {
        val (isError, supportText) = when {
            text.isEmpty() -> false to SupportTextHome.EMPTY

            text.length < 3 -> true to SupportTextHome.LOGIN_MIN_LENGTH

            !text.matches(Regex("^[a-zA-Z0-9]+$")) -> true to SupportTextHome.LOGIN_REQUIREMENTS

            else -> false to SupportTextHome.EMPTY
        }

        _uiState.value = uiState.value.copy(
            inputValue = text,
            inputError = isError,
            supportText = supportText
        )
    }


    private fun onLoginAttempt() {
        val input = uiState.value.inputValue

        if (input.isBlank()) {
            _uiState.value = uiState.value.copy(
                inputError = true,
                supportText = SupportTextHome.ENTER_LOGIN
            )
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            checkLoginUseCase(input)
                .onSuccess {
                    _navigationEvent.emit(Unit)
                }
                .onFailure { error ->
                    handleLoginError(error)
                }
        }
    }

    private suspend fun handleLoginError(error: Throwable) {
        when (error) {
            is NetworkError.NoInternet -> {
                _uiState.value = uiState.value.copy(
                    inputError = true,
                    supportText = SupportTextHome.EMPTY,
                    isLoading = false
                )

                _snackbarEvent.emit(
                    SnackbarEventParams(
                        message = NO_INTERNET,
                        duration = SnackbarDuration.Indefinite,
                        onAction = { onLoginAttempt() },
                        actionLabel = UPDATE,
                        withDismiss = true
                    )
                )
            }

            else -> {
                _uiState.value = uiState.value.copy(
                    inputError = true,
                    supportText = SupportTextHome.INVALID_LOGIN,
                    isLoading = false
                )

                _snackbarEvent.emit(SnackbarEventParams(SOMETHING_WRONG))
            }
        }
    }


    private fun requestLogin(url: String) {
        followLinkUseCase(url)
    }

    private fun clearAll() {
        _uiState.value = uiState.value.copy(inputValue = "")
    }

    companion object {
        private const val SOMETHING_WRONG = "Что-то не так, попробуйте ещё раз"
        private const val NO_INTERNET = "Нет интернета"
        private const val UPDATE = "Обновить"
    }
}