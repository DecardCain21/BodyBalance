package com.example.bodybalance.main.domain.usecase

interface CheckAuthUseCase {

    suspend operator fun invoke(): Boolean
}