package com.example.bodybalance.category.domain.usecase

public interface UpdateOrderPlaylistVideoUseCase {

    public suspend operator fun invoke(id: Int, order: Int)
}