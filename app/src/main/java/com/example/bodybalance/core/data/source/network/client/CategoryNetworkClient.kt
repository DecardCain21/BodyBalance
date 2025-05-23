package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.dto.CategoryDto
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getCategory(type: String): Result<List<CategoryDto>> {
        return doRequest { apiService.getCategory(type) }
    }
}