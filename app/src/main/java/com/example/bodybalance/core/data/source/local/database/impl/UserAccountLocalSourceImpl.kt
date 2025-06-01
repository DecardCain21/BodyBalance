package com.example.bodybalance.core.data.source.local.database.impl

import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.local.database.dao.UserAccountDao
import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity
import javax.inject.Inject

internal class UserAccountLocalSourceImpl @Inject constructor(
    private val dao: UserAccountDao
) : UserAccountLocalSource {

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return dao.getAllAccount()
    }

    override suspend fun insertAccount(account: AccountEntity) {
        dao.insertAccount(account)
    }

    override suspend fun deleteActiveAccount() {
        dao.deleteActiveAccount()
    }

    override suspend fun getActiveAccount(): AccountEntity? {
        return dao.getActiveAccount()
    }

    override suspend fun activateAccount(account: AccountEntity) {
        dao.activateAccount(account)
    }
}
