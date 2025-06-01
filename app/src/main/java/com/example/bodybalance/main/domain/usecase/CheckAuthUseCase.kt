package com.example.bodybalance.main.domain.usecase

internal interface CheckAuthUseCase {

    suspend operator fun invoke(): Boolean
}