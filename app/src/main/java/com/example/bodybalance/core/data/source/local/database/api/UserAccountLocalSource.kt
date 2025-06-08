package com.example.bodybalance.core.data.source.local.database.api

import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity

public interface UserAccountLocalSource {

    public suspend fun getAllAccounts(): List<AccountEntity>

    public suspend fun insertAccount(account: AccountEntity)

    public suspend fun deleteActiveAccount()

    public suspend fun getActiveAccount(): AccountEntity?

    public suspend fun activateAccount(account: AccountEntity)
}