package com.example.bodybalance.category.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCategoryUseCase {

   suspend operator fun invoke(): List<String>
}