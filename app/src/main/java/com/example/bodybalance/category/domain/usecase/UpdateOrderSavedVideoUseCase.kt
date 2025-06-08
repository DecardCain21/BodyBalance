package com.example.bodybalance.category.domain.usecase

public interface UpdateOrderSavedVideoUseCase {

    public suspend operator fun invoke(id: Int, order: Int)
}