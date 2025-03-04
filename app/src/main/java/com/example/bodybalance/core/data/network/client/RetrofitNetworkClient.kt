package com.example.bodybalance.core.data.network.client

import com.example.bodybalance.core.data.network.NetworkClient
import com.example.bodybalance.core.data.network.NetworkError
import com.example.bodybalance.core.util.getConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class RetrofitNetworkClient : NetworkClient {

    override suspend fun <T> doRequest(request: suspend () -> T): Result<T> {
        if (!getConnected()) { return Result.failure(NetworkError.NoInternet()) }
        return withContext(Dispatchers.IO) {
            try {
                Result.success(request())
            } catch (e: HttpException) {
                Result.failure(NetworkError.ServerError("", e.toString()))
            }
        }
    }
}