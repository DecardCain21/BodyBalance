package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.core.domain.api.UserAccountRepository
import com.example.bodybalance.core.domain.models.Account
import javax.inject.Inject

internal class GetAllAccountsUseCaseImpl @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : GetAllAccountsUseCase {

    override suspend fun invoke(): List<Account> {
        return userAccountRepository.getAllAccounts()
    }
}