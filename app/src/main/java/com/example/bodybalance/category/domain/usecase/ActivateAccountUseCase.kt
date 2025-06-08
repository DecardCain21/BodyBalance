package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Account

public interface ActivateAccountUseCase {

    public suspend operator fun invoke(account: Account)
}