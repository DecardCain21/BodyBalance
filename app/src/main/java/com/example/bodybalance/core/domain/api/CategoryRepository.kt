package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Category

interface CategoryRepository {

    suspend fun getCategory(): Result<List<Category>>
}