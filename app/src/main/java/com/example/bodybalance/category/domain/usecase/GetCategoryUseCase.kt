package com.example.bodybalance.category.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCategoryUseCase {

    operator fun invoke(): Flow<List<String>>
}