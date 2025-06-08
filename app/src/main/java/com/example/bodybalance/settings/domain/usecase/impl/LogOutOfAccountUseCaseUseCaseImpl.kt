package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.UserAccountRepository
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import javax.inject.Inject

public class LogOutOfAccountUseCaseUseCaseImpl @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : LogOutOfAccountUseCase {

    override suspend fun invoke(): Unit = userAccountRepository.deleteActiveAccount()
}