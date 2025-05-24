package com.example.bodybalance.category.domain.usecase

interface UpdateOrderPlaylistVideoUseCase {

    suspend operator fun invoke(id: Int, order: Int)
}