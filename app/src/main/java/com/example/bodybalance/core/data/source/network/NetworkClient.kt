package com.example.bodybalance.core.data.source.network

interface NetworkClient {
    suspend fun <T> doRequest(request: suspend () -> T): Result<T>
}