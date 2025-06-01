package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video

internal data class CategoryState(
    val activeAccount: Account = Account.empty(),
    val accounts: List<Account> = emptyList(),
    val category: List<Category> = emptyList(),
    val playlistVideo: List<Video> = emptyList()
)