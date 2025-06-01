package com.example.bodybalance.core.domain.api

public interface AuthRepository {

    public suspend fun isAuthenticated(): Boolean
}