package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Category

public interface CategoryRepository {

    public suspend fun getCategory(): Result<List<Category>>
}