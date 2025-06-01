package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.dto.AccountDto
import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity
import com.example.bodybalance.core.domain.models.Account

internal fun Account.convertToEntity() = AccountEntity(
    id = id,
    name = name,
    isActive = isActive
)

internal fun AccountEntity.convertToAccount() = Account(
    id = id,
    name = name,
    isActive = isActive
)

internal fun AccountDto.convertToEntity(isActive: Boolean) = AccountEntity(
    id = typeId,
    name = typeName,
    isActive = isActive
)