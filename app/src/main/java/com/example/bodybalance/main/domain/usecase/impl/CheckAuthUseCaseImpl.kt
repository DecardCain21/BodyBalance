package com.example.bodybalance.main.domain.usecase.impl

import com.example.bodybalance.core.domain.api.AuthRepository
import com.example.bodybalance.main.domain.usecase.CheckAuthUseCase
import javax.inject.Inject

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {

    override operator fun invoke(): Boolean = authRepository.isAuthenticated()
}