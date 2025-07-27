package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity
import com.example.bodybalance.core.domain.models.Account

public interface UserAccountRepository {

    public suspend fun getAllAccounts(): List<Account>

    public suspend fun deleteActiveAccount()

    public suspend fun activateAccount(account: Account)

    public suspend fun getActiveAccount(): Account
}