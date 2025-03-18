package com.example.bodybalance.home.domain.usecase

interface CheckLoginUseCase {

    suspend operator fun invoke(login: String): Result<Boolean>
}