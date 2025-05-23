package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Category

interface GetCategoryUseCase {

    suspend operator fun invoke(): Result<List<Category>>
}