package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.storage.PreferencesStorage
import com.example.bodybalance.core.data.source.network.client.LoginNetworkClient
import com.example.bodybalance.core.domain.api.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginNetworkClient: LoginNetworkClient,
    private val preferencesStorage: PreferencesStorage
) : LoginRepository {

    override suspend fun checkAccount(login: String): Result<Boolean> {
        return loginNetworkClient.checkAccount(login).onSuccess {
            preferencesStorage.login = login
        }
    }
}