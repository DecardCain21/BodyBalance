package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToCategory
import com.example.bodybalance.core.data.source.network.client.CategoryNetworkClient
import com.example.bodybalance.core.domain.api.CategoryRepository
import com.example.bodybalance.core.domain.models.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkClient: CategoryNetworkClient
) : CategoryRepository {

    override suspend fun getCategory(): Result<List<Category>> {
        return categoryNetworkClient.getCategory("basic").map { list ->
            list.map { categoryDto ->
                categoryDto.convertToCategory()
            }
        }
    }
}