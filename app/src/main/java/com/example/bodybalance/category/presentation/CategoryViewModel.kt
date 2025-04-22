package com.example.bodybalance.category.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.category.domain.usecase.GetAllSavedVideosUseCase
import com.example.bodybalance.category.presentation.state.CategoryScreenUiEvent
import com.example.bodybalance.category.presentation.state.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getAllSavedVideosUseCase: GetAllSavedVideosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun handleEvent(event: CategoryScreenUiEvent) {
        when (event) {
            is CategoryScreenUiEvent.ChangeUser -> TODO()
            is CategoryScreenUiEvent.DeleteVideo -> TODO()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getCategoryUseCase()
                val savedVideos = getAllSavedVideosUseCase().getOrNull()
                Log.e("savedVideos","$savedVideos")
                _uiState.value = CategoryState.Content(
                    category = categories,
                    savedVideo = listOf(Video(
                        id = 0.0,
                        title = "test",
                        url = "",
                        category = null,
                        description = "",
                        imageUrl = null
                    ))//emptyList()
                )
            } catch (e: Exception) {
                _uiState.value = CategoryState.Error
            }
        }
    }
}