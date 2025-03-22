package com.example.bodybalance.core.data.source.local.storage

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun isAuthenticated(): Boolean {
        return sharedPreferences.getBoolean("is_authenticated", false)
    }

    fun setAuthenticated(value: Boolean) {
        sharedPreferences.edit().putBoolean("is_authenticated", value).apply()
    }
}
