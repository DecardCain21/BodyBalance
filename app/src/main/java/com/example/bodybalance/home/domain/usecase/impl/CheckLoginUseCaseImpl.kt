package com.example.bodybalance.home.domain.usecase.impl

import com.example.bodybalance.core.domain.api.LoginRepository
import com.example.bodybalance.home.domain.usecase.CheckLoginUseCase
import javax.inject.Inject

class CheckLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : CheckLoginUseCase {

    override suspend fun invoke(login: String): Result<Boolean> {
        return loginRepository.checkAccount(login)
    }
}