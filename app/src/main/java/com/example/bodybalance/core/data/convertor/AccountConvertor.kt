package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity
import com.example.bodybalance.core.domain.models.Account

fun Account.convertToEntity() = AccountEntity(
    name = name,
    isActive = isActive
)

fun AccountEntity.convertToAccount() = Account(
    name = name,
    isActive = isActive
)