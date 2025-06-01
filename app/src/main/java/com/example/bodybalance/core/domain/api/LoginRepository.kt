package com.example.bodybalance.core.domain.api

public interface LoginRepository {

    public suspend fun checkAccount(login: String): Result<Unit>
}