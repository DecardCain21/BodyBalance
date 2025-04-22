package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity

@Dao
interface UserAccountDao {

    @Query("SELECT * FROM user_account")
    suspend fun getAllAccount(): List<AccountEntity>

    @Insert
    suspend fun insertAccount(accountEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM user_account WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveAccount(): AccountEntity

    @Query("UPDATE user_account SET isActive = 0")
    suspend fun clearActiveFlags()

    @Query("UPDATE user_account SET isActive = 1 WHERE id = :id")
    suspend fun setActiveAccount(id: Long)

    @Transaction
    suspend fun activateAccount(account: AccountEntity) {
        clearActiveFlags()
        setActiveAccount(account.id)
    }
}