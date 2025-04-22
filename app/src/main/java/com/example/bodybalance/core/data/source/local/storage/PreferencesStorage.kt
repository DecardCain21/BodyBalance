package com.example.bodybalance.core.data.source.local.storage

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    var code: String
        get() = sharedPreferences.getString(KEY_CODE, "") ?: ""
        set(value) = sharedPreferences.edit().putString(KEY_CODE, value).apply()

    companion object {
        private const val KEY_CODE = "is_authenticated"
    }
}
