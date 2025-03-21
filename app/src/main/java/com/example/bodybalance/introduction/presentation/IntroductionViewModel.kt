package com.example.bodybalance.introduction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.data.network.NetworkError
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.introduction.presentation.IntroductionScreenState.Input
import com.example.bodybalance.introduction.presentation.IntroductionScreenState.IntroductionPlayerState
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase
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
            is IntroductionScreenUiEvent.Continue -> {}
            is IntroductionScreenUiEvent.InputLogin -> enterCodeWord(event.text)
        }
    }

    fun getVideo(category: String) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = getVideoUseCase(category)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> IntroductionScreenState(
                    inputValue = Input.Empty,
                    videoState = IntroductionPlayerState.Empty
                )

                else -> result.getOrNull()?.let {
                    /*IntroductionScreenState(
                        inputValue = Input.Empty, IntroductionPlayerState.Content(
                            videoUrl = it.videoItems.map { video -> video.url }.first()
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

    private fun hardCode(result: List<Video>): IntroductionScreenState {
        return IntroductionScreenState(
            inputValue = Input.Text("Marat"), IntroductionPlayerState.Content(
                videoUrl = result.map { video -> video.url }.first()
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
}