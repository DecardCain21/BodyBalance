package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.core.domain.api.CategoryRepository
import com.example.bodybalance.core.domain.models.Category
import javax.inject.Inject

public class GetCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : GetCategoryUseCase {

    override suspend operator fun invoke(): Result<List<Category>> =
        categoryRepository.getCategory()
}