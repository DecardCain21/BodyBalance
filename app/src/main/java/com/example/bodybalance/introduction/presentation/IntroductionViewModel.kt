package com.example.bodybalance.introduction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.core.data.network.NetworkError
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
    private val _uiState = MutableStateFlow<IntroductionState>(IntroductionState.Loading)
    val uiState: StateFlow<IntroductionState> = _uiState.asStateFlow()

    private var isInitialized = false

    fun getVideo(category: String) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = getVideoUseCase(category)
            val newState = when (result.exceptionOrNull()) {
                is NetworkError.ServerError,
                is NetworkError.NoData,
                is NetworkError.NoInternet -> IntroductionState.Empty

                else -> result.getOrNull()?.let {
                    println(it.videoItems.map { video -> video.url }.first())
                    IntroductionState.Content(
                        videoUrl = it.videoItems.map { video -> video.url }.first()
                    )
                } ?: IntroductionState.Empty
            }
            _uiState.value = newState
        }
    }
}