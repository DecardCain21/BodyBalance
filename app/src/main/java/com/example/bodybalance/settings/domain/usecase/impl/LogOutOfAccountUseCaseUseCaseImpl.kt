package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.AuthRepository
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import javax.inject.Inject

class LogOutOfAccountUseCaseUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): LogOutOfAccountUseCase {

    override fun invoke() = authRepository.logout()
}