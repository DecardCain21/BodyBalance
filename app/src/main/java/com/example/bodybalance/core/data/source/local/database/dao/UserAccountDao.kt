package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity

@Dao
internal interface UserAccountDao {

    @Query("SELECT * FROM user_account")
    suspend fun getAllAccount(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(accountEntity: AccountEntity)

    @Query("DELETE FROM user_account WHERE name = :name")
    suspend fun deleteAccount(name: String)

    @Query("SELECT * FROM user_account WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveAccount(): AccountEntity?

    @Query("UPDATE user_account SET isActive = 0")
    suspend fun clearActiveFlags()

    @Query("UPDATE user_account SET isActive = 1 WHERE name = :name")
    suspend fun setActiveAccount(name: String)

    @Transaction
    suspend fun deleteActiveAccount() {
        getActiveAccount()?.let {
            deleteAccount(it.name)
        }
    }

    @Transaction
    suspend fun activateAccount(account: AccountEntity) {
        clearActiveFlags()
        setActiveAccount(account.name)
    }

    @Transaction
    suspend fun insertAccount(accountEntity: AccountEntity) {
        clearActiveFlags()
        addAccount(accountEntity)
    }
}