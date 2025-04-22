package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.domain.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userAccountLocalSource: UserAccountLocalSource
) : AuthRepository {

    override suspend fun isAuthenticated(): Boolean {
        return userAccountLocalSource.getActiveAccount() != null
    }
}