package com.example.bodybalance.core.data.source.local.database.impl

import com.example.bodybalance.core.data.convertor.convertToAccount
import com.example.bodybalance.core.data.convertor.convertToEntity
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.local.database.dao.UserAccountDao
import com.example.bodybalance.core.domain.models.Account
import javax.inject.Inject

class UserAccountLocalSourceImpl @Inject constructor(
    private val dao: UserAccountDao
) : UserAccountLocalSource {

    override suspend fun getAllAccounts(): List<Account> {
        return dao.getAllAccount().map { list -> list.convertToAccount() }
    }

    override suspend fun insertAccount(account: Account) {
        dao.insertAccount(account.convertToEntity())
    }

    override suspend fun deleteAccount(account: Account) {
        dao.deleteAccount(account.name)
    }

    override suspend fun getActiveAccount(): Account? {
        return dao.getActiveAccount()?.convertToAccount()
    }

    override suspend fun activateAccount(account: Account) {
        dao.activateAccount(account.convertToEntity())
    }
}
