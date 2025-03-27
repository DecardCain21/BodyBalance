package com.example.bodybalance.core.data.source.local.storage

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    var login: String
        get() = sharedPreferences.getString(KEY_IS_AUTHENTICATED, "") ?: ""
        set(value) = sharedPreferences.edit().putString(KEY_IS_AUTHENTICATED, value).apply()

    fun isAuthenticated(): Boolean = login.isNotEmpty()

    companion object {
        private const val KEY_IS_AUTHENTICATED = "is_authenticated"
    }
}
