package com.example.bodybalance.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.videoplayer.domain.usecase.GetAllVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getAllVideosUseCase: GetAllVideosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun handleEvent(event: CategoryScreenUiEvent) {
        when (event) {
            CategoryScreenUiEvent.ChangeUser -> TODO()
            is CategoryScreenUiEvent.DeleteVideo -> TODO()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getCategoryUseCase()
                val savedVideos = getAllVideosUseCase().getOrNull()
                _uiState.value = CategoryState.Content(
                    category = categories,
                    playlist = savedVideos ?: emptyList()
                )
            } catch (e: Exception) {
                _uiState.value = CategoryState.Error
            }
        }
    }
}