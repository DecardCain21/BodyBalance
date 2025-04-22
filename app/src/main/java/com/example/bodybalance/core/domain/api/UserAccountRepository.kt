package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Account

interface UserAccountRepository {

    suspend fun getAllAccounts(): List<Account>

    suspend fun deleteAccount(account: Account)

    suspend fun activateAccount(account: Account)
}