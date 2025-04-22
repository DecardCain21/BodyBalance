package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.DeleteAccountUseCase
import com.example.bodybalance.core.domain.api.UserAccountRepository
import com.example.bodybalance.core.domain.models.Account
import javax.inject.Inject

class DeleteAccountUseCaseImpl @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : DeleteAccountUseCase {

    override suspend fun invoke(account: Account) {
        userAccountRepository.deleteAccount(account)
    }
}