package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToCategory
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.network.client.CategoryNetworkClient
import com.example.bodybalance.core.domain.api.CategoryRepository
import com.example.bodybalance.core.domain.models.Category
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkClient: CategoryNetworkClient,
    private val userAccountLocalSource: UserAccountLocalSource
) : CategoryRepository {

    override suspend fun getCategory(): Result<List<Category>> {
        val accountTypeId =
            userAccountLocalSource.getActiveAccount()?.id ?: return Result.failure(Exception())
        return categoryNetworkClient.getCategory(accountTypeId).map { list ->
            list.map { categoryDto ->
                categoryDto.convertToCategory()
            }
        }
    }
}