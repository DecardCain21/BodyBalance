package com.example.bodybalance.core.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_account",
    indices = [Index(value = ["name"], unique = true)]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val isActive: Boolean
)
