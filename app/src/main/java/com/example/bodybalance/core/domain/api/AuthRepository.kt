package com.example.bodybalance.core.domain.api

interface AuthRepository {

    suspend fun isAuthenticated(): Boolean
}