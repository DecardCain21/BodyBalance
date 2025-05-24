package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToEntity
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.network.client.LoginNetworkClient
import com.example.bodybalance.core.domain.api.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginNetworkClient: LoginNetworkClient,
    private val userAccountLocalSource: UserAccountLocalSource
) : LoginRepository {

    override suspend fun checkAccount(login: String): Result<Unit> {
        val result = loginNetworkClient.checkAccount(login)
        return if (result.isSuccess) {
            userAccountLocalSource.insertAccount(
                result.getOrNull()?.convertToEntity(isActive = true)!!
            )
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}