package com.example.bodybalance.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.category.domain.usecase.GetAllPlaylistVideosUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.category.presentation.state.CategoryScreenUiEvent
import com.example.bodybalance.category.presentation.state.CategoryState
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getAllPlaylistVideosUseCase: GetAllPlaylistVideosUseCase,
    private val activateAccountUseCase: ActivateAccountUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun handleEvent(event: CategoryScreenUiEvent) {
        when (event) {
            is CategoryScreenUiEvent.ChangeUser -> changeUserAccount(event.account)
            is CategoryScreenUiEvent.DeleteVideo -> deleteVideoFromPlaylist(event.video)
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val accounts = getAllAccountsUseCase()
                val categories = getCategoryUseCase()
                getAllPlaylistVideosUseCase().collect { playlistVideo ->
                    _uiState.value = CategoryState.Content(
                        activeAccount = accounts.find { it.isActive } ?: accounts.first(),
                        accounts = accounts,
                        category = categories,
                        savedVideo = playlistVideo
                    )
                }

            } catch (e: Exception) {
                _uiState.value = CategoryState.Error
            }
        }
    }

    private fun changeUserAccount(account: Account) {
        viewModelScope.launch {
            _uiState.update { state ->
                if (state is CategoryState.Content) {
                    state.copy(activeAccount = account)
                } else {
                    state
                }
            }
            activateAccountUseCase(account)
        }
    }

    private fun deleteVideoFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video)
        }
    }
}