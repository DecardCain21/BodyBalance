package com.example.bodybalance.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.category.domain.usecase.GetAllSavedVideoFlowUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.category.domain.usecase.UpdateOrderPlaylistVideoUseCase
import com.example.bodybalance.category.domain.usecase.UpdateOrderSavedVideoUseCase
import com.example.bodybalance.category.presentation.state.CategoryScreenState
import com.example.bodybalance.category.presentation.state.CategoryScreenUiEvent
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.DeleteSavedVideoUseCase
import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val fileDownloaderImpl: FileDownloader,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getAllPlaylistVideosUseCase: GetAllPlaylistVideosUseCase,
    private val activateAccountUseCase: ActivateAccountUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val deletePlaylistVideoUseCase: DeletePlaylistVideoUseCase,
    private val updateOrderPlaylistVideoUseCase: UpdateOrderPlaylistVideoUseCase,
    private val updateOrderSavedVideoUseCase: UpdateOrderSavedVideoUseCase,
    private val getAllSavedVideoFlowUseCase: GetAllSavedVideoFlowUseCase,
    private val deleteSavedVideoUseCase: DeleteSavedVideoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryScreenState.emptyState())
    val uiState: StateFlow<CategoryScreenState> = _uiState.asStateFlow()

    init {
        loadInformation()
    }

    fun handleEvent(event: CategoryScreenUiEvent) {
        when (event) {
            is CategoryScreenUiEvent.ChangeUser -> changeUserAccount(event.account)
            is CategoryScreenUiEvent.DeleteVideo -> deleteVideoFromPlaylist(event.video)
            is CategoryScreenUiEvent.UpdateOrderPlaylistVideo -> updateOrderPlaylistVideo(
                id = event.id,
                order = event.order
            )

            is CategoryScreenUiEvent.DeleteSavedVideo -> deleteVideoFromCache(event.video)
            is CategoryScreenUiEvent.UpdateOrderSavedVideo -> updateOrderSavedVideo(
                id = event.id,
                order = event.order
            )

            is CategoryScreenUiEvent.UpdateExercise -> updateExercise()
            is CategoryScreenUiEvent.PullToRefreshExercise -> pullToRefreshExercise()
        }
    }

    private fun pullToRefreshExercise() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCategoryRefreshing = true) }
            val categories = getCategoryUseCase().getOrNull()
            val categoryState = if (categories.isNullOrEmpty()) {
                CategoryScreenState.CategoryState.Empty
            } else {
                CategoryScreenState.CategoryState.Content(categories)
            }

            _uiState.update { it.copy(category = categoryState, isCategoryRefreshing = false) }
        }
    }

    private fun loadInformation() {
        viewModelScope.launch {
            try {
                val accounts = getAllAccountsUseCase()
                val categories = getCategoryUseCase().getOrNull()

                val categoryState = if (categories.isNullOrEmpty()) {
                    CategoryScreenState.CategoryState.Empty
                } else {
                    CategoryScreenState.CategoryState.Content(categories)
                }

                // Комбинируем два Flow без вложенных collect
                combine(
                    getAllPlaylistVideosUseCase(),
                    getAllSavedVideoFlowUseCase()
                ) { playlistVideo, savedVideo ->
                    val playlistState = if (playlistVideo.isEmpty()) {
                        CategoryScreenState.PlaylistState.Empty
                    } else {
                        CategoryScreenState.PlaylistState.Content(playlistVideo)
                    }

                    val downloadedState = if (savedVideo.isEmpty()) {
                        CategoryScreenState.DownloadedState.Empty
                    } else {
                        CategoryScreenState.DownloadedState.Content(savedVideo)
                    }

                    val activeAccount = accounts.find { it.isActive } ?: accounts.first()
                    CategoryScreenState(
                        activeAccount = activeAccount,
                        accounts = CategoryScreenState.AccountsState.Content(accounts),
                        category = categoryState,
                        playlistVideo = playlistState,
                        downloadedState = downloadedState
                    )
                }.collect { newState ->
                    _uiState.update { newState }
                }
            } catch (e: Exception) {
                _uiState.update { CategoryScreenState.emptyState() }
            }
        }
    }

    private fun updateExercise() {
        viewModelScope.launch {
            val categories = getCategoryUseCase().getOrNull()
            val categoryState = if (categories.isNullOrEmpty()) {
                CategoryScreenState.CategoryState.Empty
            } else {
                CategoryScreenState.CategoryState.Content(categories)
            }
            _uiState.update { state ->
                state.copy(
                    category = categoryState
                )
            }
        }
    }

    private fun changeUserAccount(account: Account) {
        _uiState.update { it.copy(category = CategoryScreenState.CategoryState.Loading) }
        viewModelScope.launch {
            activateAccountUseCase(account)
            val categories = getCategoryUseCase().getOrNull()

            val categoryState = if (categories.isNullOrEmpty()) {
                CategoryScreenState.CategoryState.Empty
            } else {
                CategoryScreenState.CategoryState.Content(categories)
            }
            _uiState.update { state ->
                state.copy(
                    activeAccount = account,
                    category = categoryState
                )
            }
        }
    }

    private fun deleteVideoFromPlaylist(video: Video) {
        viewModelScope.launch {
            deletePlaylistVideoUseCase(video)
        }
    }

    private fun updateOrderPlaylistVideo(id: Int, order: Int) {
        viewModelScope.launch {
            updateOrderPlaylistVideoUseCase(id = id, order = order)
        }
    }

    private fun deleteVideoFromCache(video: Video) {
        fileDownloaderImpl.deleteFile(fileName = video.id.toString()).let {
            viewModelScope.launch {
                deleteSavedVideoUseCase(video)
            }
        }
        viewModelScope.launch {
            deleteSavedVideoUseCase(video = video)
        }
    }


    private fun updateOrderSavedVideo(id: Int, order: Int) {
        viewModelScope.launch {
            updateOrderSavedVideoUseCase(id = id, order = order)
        }
    }
}