package com.example.bodybalance.core.util

import android.content.Context
import com.example.bodybalance.app.BodyBalanceApp
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun getConnected(): Boolean {
    val connectivityManager = BodyBalanceApp
        .applicationContext()
        .getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return if (capabilities != null) {
        with(capabilities) {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    } else {
        false
    }
}