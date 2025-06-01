package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video

internal data class CategoryScreenState(
    val activeAccount: Account = Account.empty(),
    val accounts: AccountsState,
    val category: CategoryState,
    val playlistVideo: PlaylistState
) {

    sealed interface AccountsState {
        data object Empty : AccountsState
        data class Content(val accountList: List<Account>) : AccountsState
    }

    sealed interface CategoryState {
        data object Empty : CategoryState
        data class Content(val categoryList: List<Category>) : CategoryState
    }

    sealed interface PlaylistState {
        data object Empty : PlaylistState
        data class Content(val playlistVideo: List<Video>) : PlaylistState
    }

    companion object {
        fun emptyState(): CategoryScreenState =
            CategoryScreenState(
                accounts = AccountsState.Empty,
                category = CategoryState.Empty,
                playlistVideo = PlaylistState.Empty
            )
    }
}