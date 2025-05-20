package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Video

sealed interface CategoryState {

    data class Content(
        val activeAccount: Account,
        val accounts: List<Account>,
        val category: List<String>,
        val savedVideo: List<Video>
    ) : CategoryState

    data object Loading : CategoryState
    data object Error : CategoryState
}