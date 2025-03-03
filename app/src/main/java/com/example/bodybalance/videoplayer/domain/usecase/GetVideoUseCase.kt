package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Category

interface GetVideoUseCase {

    suspend operator fun invoke(category: String): Result<Category>
}