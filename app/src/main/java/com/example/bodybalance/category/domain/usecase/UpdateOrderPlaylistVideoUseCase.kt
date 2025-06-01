package com.example.bodybalance.category.domain.usecase

internal interface UpdateOrderPlaylistVideoUseCase {

    suspend operator fun invoke(id: Int, order: Int)
}