package com.example.bodybalance.core.data.source.network.client

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.bodybalance.core.data.source.network.NetworkClient
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.getConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
abstract class RetrofitNetworkClient : NetworkClient {

    private val maxRetries = 10
    private val retryDelayMillis = 2000L

    override suspend fun <T> doRequest(request: suspend () -> T): Result<T> {

        if (!getConnected()) {
            return Result.failure(NetworkError.NoInternet())
        }

        var currentAttempt = 0

        return withContext(Dispatchers.IO) {

            while (currentAttempt < maxRetries) {
                try {
                    return@withContext Result.success(request())
                } catch (e: HttpException) {
                    return@withContext Result.failure(NetworkError.ServerError("", e.toString()))
                } catch (e: SocketTimeoutException) {
                    Log.e("SocketTimeoutException", "Попытка № $currentAttempt")
                    currentAttempt++
                    if (currentAttempt >= maxRetries) {
                        return@withContext Result.failure(
                            NetworkError.ServerError("", e.toString())
                        )
                    } else {
                        delay(retryDelayMillis)
                    }
                }
            }
            Result.failure(NetworkError.ServerError("", ""))
        }
    }
}