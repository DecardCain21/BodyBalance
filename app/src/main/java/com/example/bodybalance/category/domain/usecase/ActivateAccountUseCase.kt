package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Account

interface ActivateAccountUseCase {

    suspend operator fun invoke(account: Account)
}