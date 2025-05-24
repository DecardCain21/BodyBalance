package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Account

interface UserAccountRepository {

    suspend fun getAllAccounts(): List<Account>

    suspend fun deleteActiveAccount()

    suspend fun activateAccount(account: Account)
}