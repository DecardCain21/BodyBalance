package com.example.bodybalance.main.domain.usecase

public interface CheckAuthUseCase {

    public suspend operator fun invoke(): Boolean
}