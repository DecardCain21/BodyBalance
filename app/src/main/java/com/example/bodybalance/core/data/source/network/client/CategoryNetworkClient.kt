package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.source.network.BodyBalanceApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    fun getCategory(): Flow<List<String>> = flow {
        val result = doRequest { apiService.getCategory() }
        emit(result.getOrElse { emptyList() })
    }.flowOn(Dispatchers.IO)
}