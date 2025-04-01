package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.AuthRepository
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccount
import javax.inject.Inject

class LogOutOfAccountImpl @Inject constructor(
    private val authRepository: AuthRepository
): LogOutOfAccount {

    override fun invoke() = authRepository.logout()
}