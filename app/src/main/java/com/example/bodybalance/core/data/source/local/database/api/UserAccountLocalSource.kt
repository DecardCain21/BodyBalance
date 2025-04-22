package com.example.bodybalance.core.data.source.local.database.api

import com.example.bodybalance.core.domain.models.Account

interface UserAccountLocalSource {

    suspend fun getAllAccounts(): List<Account>

    suspend fun insertAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    suspend fun getActiveAccount(): Account

    suspend fun activateAccount(account: Account)
}
