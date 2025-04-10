package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.core.domain.api.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : GetCategoryUseCase {

    override suspend operator fun invoke(): List<String> = categoryRepository.getCategory().first()
        // flow { emit(listOf("Шея", "Плечо", "Колено", "Голеностоп", "Планки", "Отжимания у стены")) }
}