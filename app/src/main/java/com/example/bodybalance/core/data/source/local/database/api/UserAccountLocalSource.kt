package com.example.bodybalance.core.data.source.local.database.api

import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity

interface UserAccountLocalSource {

    suspend fun getAllAccounts(): List<AccountEntity>

    suspend fun insertAccount(account: AccountEntity)

    suspend fun deleteActiveAccount()

    suspend fun getActiveAccount(): AccountEntity?

    suspend fun activateAccount(account: AccountEntity)
}