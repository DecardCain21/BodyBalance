package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.network.client.CategoryNetworkClient
import com.example.bodybalance.core.domain.api.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkClient: CategoryNetworkClient
) : CategoryRepository {

    override fun getCategory(): Flow<List<String>> {
        return categoryNetworkClient.getCategory()
    }
}