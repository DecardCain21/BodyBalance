package com.example.bodybalance.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class BodyBalanceApp : Application() {

    init {
        instance = this
    }

    public companion object {
        private var instance: BodyBalanceApp? = null

        public fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}