package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.dto.AccountDto
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService

class LoginNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun checkAccount(login: String): Result<AccountDto> {
        return super.doRequest { apiService.checkAccount(username = login) }
    }
}