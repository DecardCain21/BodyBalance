package com.example.bodybalance.home.domain.usecase

public interface CheckLoginUseCase {

    public suspend operator fun invoke(login: String): Result<Unit>
}