package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Account

interface GetAllAccountsUseCase {

    suspend operator fun invoke(): List<Account>
}