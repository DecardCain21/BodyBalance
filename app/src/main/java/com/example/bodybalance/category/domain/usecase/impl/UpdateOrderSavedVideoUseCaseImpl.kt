package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.UpdateOrderSavedVideoUseCase
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import javax.inject.Inject

public class UpdateOrderSavedVideoUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : UpdateOrderSavedVideoUseCase {
    override suspend fun invoke(id: Int, order: Int) {
        savedVideoRepository.updateOrderSavedVideo(id = id, order = order)
    }
}