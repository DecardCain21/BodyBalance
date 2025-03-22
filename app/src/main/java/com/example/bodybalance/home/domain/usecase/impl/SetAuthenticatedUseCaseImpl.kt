package com.example.bodybalance.home.domain.usecase.impl

import com.example.bodybalance.core.domain.api.AuthRepository
import com.example.bodybalance.home.domain.usecase.SetAuthenticatedUseCase
import javax.inject.Inject

class SetAuthenticatedUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SetAuthenticatedUseCase {

    override fun invoke(value: Boolean) = authRepository.setAuthenticated(value)
}