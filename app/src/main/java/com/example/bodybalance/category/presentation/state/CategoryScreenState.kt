package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video

internal data class CategoryScreenState(
    val activeAccount: Account = Account.empty(),
    val accounts: AccountsState,
    val category: CategoryState,
    val playlistVideo: PlaylistState,
    val downloadedState: DownloadedState,
    val isCategoryRefreshing: Boolean = false
) {

    sealed interface AccountsState {
        data object Empty : AccountsState
        data class Content(val accountList: List<Account>) : AccountsState
    }

    sealed interface CategoryState {
        data object Empty : CategoryState
        data object Loading : CategoryState
        data class Content(val categoryList: List<Category>) : CategoryState
    }

    sealed interface PlaylistState {
        data object Empty : PlaylistState
        data class Content(val playlistVideo: List<Video>) : PlaylistState
    }

    sealed interface DownloadedState {
        data object Empty : DownloadedState
        data class Content(val downloadedVideo: List<Video>) : DownloadedState
    }

    companion object {
        fun emptyState(): CategoryScreenState =
            CategoryScreenState(
                accounts = AccountsState.Empty,
                category = CategoryState.Loading,
                playlistVideo = PlaylistState.Empty,
                downloadedState = DownloadedState.Empty
            )
    }
}