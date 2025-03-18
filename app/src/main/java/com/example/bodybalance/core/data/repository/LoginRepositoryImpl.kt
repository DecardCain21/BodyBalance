package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.network.client.LoginNetworkClient
import com.example.bodybalance.core.domain.api.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginNetworkClient: LoginNetworkClient
) : LoginRepository {

    override suspend fun checkAccount(login: String): Result<Boolean> {
        return loginNetworkClient.checkAccount(login)
    }
}