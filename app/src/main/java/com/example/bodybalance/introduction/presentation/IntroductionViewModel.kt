package com.example.bodybalance.introduction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionCodeUseCase
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionVideoUseCase
import com.example.bodybalance.introduction.domain.usecase.SetIntroductionCodeUseCase
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState.Input
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState.IntroductionPlayerState
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenUiEvent
import com.example.bodybalance.introduction.presentation.state.SupportTextIntroduction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class IntroductionViewModel @Inject constructor(
    private val getIntroductionCodeUseCase: GetIntroductionCodeUseCase,
    private val setIntroductionCodeUseCase: SetIntroductionCodeUseCase,
    private val getIntroductionVideoUseCase: GetIntroductionVideoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(IntroductionScreenState.emptyState())
    val uiState: StateFlow<IntroductionScreenState>
        get() = _uiState.asStateFlow()

    init { getIntroductionVideo() }

    fun handleEvent(event: IntroductionScreenUiEvent) {
        when (event) {
            is IntroductionScreenUiEvent.Continue -> eventContinue()
            is IntroductionScreenUiEvent.InputLogin -> enterCodeWord(event.text)
        }
    }

    private fun getIntroductionVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            val code = getIntroductionCodeUseCase()
            val inputState = if (code.isNotEmpty()) Input.Text(code) else Input.Empty
            getIntroductionVideoUseCase.unpackVideoIfNeeded()
            _uiState.value = IntroductionScreenState(
                inputValue = inputState, IntroductionPlayerState.Content(
                    video = Video.emptyVideo(1).copy(
                        remoteVideoUrl = getIntroductionVideoUseCase(),
                        category = "Введение",
                        description = """
                            Это видео поможет вам быстро разобраться, как всё работает. 
                            После просмотра введите кодовое слово из видео, чтобы продолжить
                        """.trimIndent(),
                        name = "Введение"
                    )
                ), buttonIsEnabled = code.isNotEmpty()
            )
        }
    }

    private fun eventContinue() {
        val code = (_uiState.value.inputValue as Input.Text).value
        setIntroductionCodeUseCase(code)
    }

    private fun enterCodeWord(input: String) {
        var supportText: String = SupportTextIntroduction.ENTER_LOGIN.message
        val isEnabled: Boolean = when (input) {
            "Marat" -> true
            "Nikita" -> true
            "Anastasia" -> true
            "" -> {
                supportText = SupportTextIntroduction.ENTER_LOGIN.message
                false
            }

            else -> {
                supportText = SupportTextIntroduction.INVALID_LOGIN.message
                false
            }
        }
        _uiState.value = uiState.value.copy(
            inputValue = Input.Text(input),
            buttonIsEnabled = isEnabled,
            supportText = supportText
        )
    }
}