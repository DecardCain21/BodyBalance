package com.example.bodybalance.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BodyBalanceApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: BodyBalanceApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}