package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.domain.api.UserAccountRepository
import com.example.bodybalance.core.domain.models.Account
import javax.inject.Inject

class UserAccountRepositoryImpl @Inject constructor(
    private val userAccountLocalSource: UserAccountLocalSource
) : UserAccountRepository {

    override suspend fun getAllAccounts(): List<Account> {
        return userAccountLocalSource.getAllAccounts()
    }

    override suspend fun deleteAccount() {
        val currentAccount = userAccountLocalSource.getActiveAccount()
        userAccountLocalSource.deleteAccount(currentAccount)
    }

    override suspend fun activateAccount(account: Account) {
        userAccountLocalSource.activateAccount(account)
    }
}