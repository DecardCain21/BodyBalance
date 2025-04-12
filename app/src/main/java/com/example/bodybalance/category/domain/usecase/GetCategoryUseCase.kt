package com.example.bodybalance.category.domain.usecase

interface GetCategoryUseCase {

    suspend operator fun invoke(): List<String>
}