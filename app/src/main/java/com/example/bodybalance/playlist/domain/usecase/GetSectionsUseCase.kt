package com.example.bodybalance.playlist.domain.usecase

interface GetSectionsUseCase {
    operator fun invoke(): List<String>
}