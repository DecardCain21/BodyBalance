package com.example.bodybalance.core.data.source.network.client

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.bodybalance.core.data.source.network.NetworkClient
import com.example.bodybalance.core.util.NetworkError
import com.example.bodybalance.core.util.getConnected
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

public abstract class RetrofitNetworkClient : NetworkClient {
    private val maxRetries = 10
    private val retryDelayMillis = 2000L
    public open val firebaseAnalytics: FirebaseAnalytics? = null

    private val deviceData = hashMapOf(
        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date(System.currentTimeMillis())),
        "device_info" to mapOf(
            "manufacturer" to Build.MANUFACTURER,
            "model" to Build.MODEL,
            "display_name" to getDeviceName(),
            "android" to Build.VERSION.RELEASE,
        )
    )

    override suspend fun <T> doRequest(request: suspend () -> T): Result<T> {

        if (!getConnected()) {
            return Result.failure(NetworkError.NoInternet())
        }

        var currentAttempt = 0

        return withContext(Dispatchers.IO) {

            while (currentAttempt < maxRetries) {
                try {
                    firebaseAnalytics?.logEvent("network_attempt", Bundle().apply {
                        putInt("attempt_number", currentAttempt)
                        putString("status", "start")
                        putString("timestamp", deviceData["timestamp"] as String)
                        val deviceInfo = deviceData["device_info"] as Map<*, *>
                        putString("manufacturer", deviceInfo["manufacturer"] as String)
                        putString("model", deviceInfo["model"] as String)
                        putString("display_name", deviceInfo["display_name"] as String)
                        putString("android", deviceInfo["android"] as String)
                    })

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

    private fun getDeviceName(): String {
        return if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
            Build.MODEL
        } else {
            "${Build.MANUFACTURER} ${Build.MODEL}"
        }
    }
}