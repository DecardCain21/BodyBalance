package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Category

interface GetVideoUseCase {

    suspend operator fun invoke(category: String): Result<Category>
}