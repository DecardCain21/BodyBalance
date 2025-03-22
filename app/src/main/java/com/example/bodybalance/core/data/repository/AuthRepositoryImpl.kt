package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.storage.PreferencesStorage
import com.example.bodybalance.core.domain.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : AuthRepository {

    override fun isAuthenticated(): Boolean = preferencesStorage.isAuthenticated()

    override fun setAuthenticated(value: Boolean) {
        preferencesStorage.setAuthenticated(value)
    }
}