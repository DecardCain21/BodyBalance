package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Account

public interface GetAllAccountsUseCase {

    public suspend operator fun invoke(): List<Account>

    public suspend fun testGetActiveAccount():Account
}