package com.example.bodybalance.core.data.network.client

import com.example.bodybalance.core.data.network.BodyBalanceApiService

class LoginNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun checkAccount(login: String): Result<Boolean> {
        return super.doRequest {
            apiService.checkAccount(type = login)
        }
    }
}