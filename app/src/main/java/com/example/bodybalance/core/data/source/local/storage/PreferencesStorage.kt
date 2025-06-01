package com.example.bodybalance.core.data.source.local.storage

import android.content.SharedPreferences
import javax.inject.Inject

public class PreferencesStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    public var code: String
        get() = sharedPreferences.getString(KEY_CODE, "") ?: ""
        set(value) = sharedPreferences.edit().putString(KEY_CODE, value).apply()

    public var useOnlyWifi: Boolean
        get() = sharedPreferences.getBoolean(KEY_WIFI, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_WIFI, value).apply()

    public companion object {
        private const val KEY_CODE = "is_authenticated"
        private const val KEY_WIFI = "download_only_with_wifi"
    }
}