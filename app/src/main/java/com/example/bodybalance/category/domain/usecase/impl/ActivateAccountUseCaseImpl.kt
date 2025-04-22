package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.core.domain.api.UserAccountRepository
import com.example.bodybalance.core.domain.models.Account
import javax.inject.Inject

class ActivateAccountUseCaseImpl @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : ActivateAccountUseCase {

    override suspend fun invoke(account: Account) {
        userAccountRepository.activateAccount(account)
    }
}