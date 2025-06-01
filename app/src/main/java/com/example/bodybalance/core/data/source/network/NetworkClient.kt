package com.example.bodybalance.core.data.source.network

public interface NetworkClient {
    public suspend fun <T> doRequest(request: suspend () -> T): Result<T>
}