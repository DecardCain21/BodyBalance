package com.example.bodybalance.introduction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetVideoUseCase
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionCodeUseCase
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
class IntroductionViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase,
    private val getIntroductionCodeUseCase: GetIntroductionCodeUseCase,
    private val setIntroductionCodeUseCase: SetIntroductionCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        IntroductionScreenState(
            inputValue = Input.Empty,
            videoState = IntroductionPlayerState.Loading,
        )
    )
    val uiState: StateFlow<IntroductionScreenState>
        get() = _uiState.asStateFlow()

    private var isInitialized = false

    fun handleEvent(event: IntroductionScreenUiEvent) {
        when (event) {
            is IntroductionScreenUiEvent.Continue -> eventContinue()
            is IntroductionScreenUiEvent.InputLogin -> enterCodeWord(event.text)
        }
    }

    fun getVideo(category: String) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch(Dispatchers.IO) {
            val code = getIntroductionCodeUseCase()
            val inputState = if (code.isNotEmpty()) Input.Text(code) else Input.Empty

            val result = getVideoUseCase(category)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> IntroductionScreenState(
                    inputValue = inputState,
                    videoState = IntroductionPlayerState.Empty
                )

                else -> result.getOrNull()?.let {
                    /*IntroductionScreenState(
                        inputValue = inputState, IntroductionPlayerState.Content(
                            videoUrl = it.videoItems.map { video -> video.url }.first()
                        )
                    )*/
                    /*savedVideoUseCase(
                        video = Video(
                            id = 1.2,
                            url = it.videoItems[0].url,
                            category = "test",
                            title = "test name",
                            description = "test description"
                        )
                    )*/
                    hardCode(result.getOrNull()!!.videoItems)
                } ?: IntroductionScreenState(
                    inputValue = Input.Empty, IntroductionPlayerState.Empty
                )
            }
            _uiState.value = newState
        }
    }

    private fun eventContinue() {
        setIntroductionCodeUseCase(CODE)
    }

    private fun hardCode(result: List<Video>): IntroductionScreenState {
        return IntroductionScreenState(
            inputValue = Input.Text("Marat"), IntroductionPlayerState.Content(
                video = result.map { video -> video }.first()
            ), buttonIsEnabled = true
        )
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
        _uiState.value =
            uiState.value.copy(
                inputValue = Input.Text(input),
                buttonIsEnabled = isEnabled,
                supportText = supportText
            )
    }

    companion object {
        const val CODE = "1234"
    }
}