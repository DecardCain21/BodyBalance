package com.example.bodybalance.core.domain.api

interface LoginRepository {

    suspend fun checkAccount(login: String): Result<Boolean>
}