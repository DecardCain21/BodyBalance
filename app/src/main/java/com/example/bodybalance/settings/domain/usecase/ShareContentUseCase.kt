package com.example.bodybalance.settings.domain.usecase

interface ShareContentUseCase {

    operator fun invoke(text: String, title: String)
}