package com.example.bodybalance.core.domain.usecase.api

interface ShareContentUseCase {

    operator fun invoke(text: String, title: String)
}