package com.example.bodybalance.core.data.source.network.client

import android.util.Log
import com.example.bodybalance.core.data.source.network.NetworkClient
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.getConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

abstract class RetrofitNetworkClient : NetworkClient {

    private val maxRetries = 10
    private val retryDelayMillis = 2000L

    override suspend fun <T> doRequest(request: suspend () -> T): Result<T> {

        if (!getConnected()) { return Result.failure(NetworkError.NoInternet()) }

        var currentAttempt = 0

        return withContext(Dispatchers.IO) {

            while (currentAttempt < maxRetries) {
                try {
                    return@withContext Result.success(request())
                } catch (e: retrofit2.HttpException) {
                    return@withContext when (e.code()) {
                        500 -> Result.failure(NetworkError.ServerError(e.message()))
                        404 -> Result.failure(NetworkError.NoData())
                        400 -> Result.failure(NetworkError.BadRequest())
                        else -> Result.failure(e)
                    }
                } catch (e: SocketTimeoutException) {
                    Log.e("SocketTimeoutException", "Попытка № $currentAttempt")
                    currentAttempt++
                    if (currentAttempt >= maxRetries) {
                        return@withContext Result.failure(
                            NetworkError.ServerError(e.message ?: "")
                        )
                    } else {
                        delay(retryDelayMillis)
                    }
                } catch (e: Exception) {
                    Log.e("test_exception", "${e.message}")
                    return@withContext Result.failure(NetworkError.ServerError(e.message ?: ""))
                }
            }
            Result.failure(NetworkError.ServerError(""))
        }
    }
}