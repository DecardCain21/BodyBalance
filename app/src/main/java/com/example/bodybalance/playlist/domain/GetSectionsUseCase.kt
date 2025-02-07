package com.example.bodybalance.playlist.domain

interface GetSectionsUseCase {
    operator fun invoke(): List<String>
}