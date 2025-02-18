package com.example.bodybalance.core.data.network

interface NetworkClient {
    suspend fun <T> doRequest(request: suspend () -> T): Result<T>
}