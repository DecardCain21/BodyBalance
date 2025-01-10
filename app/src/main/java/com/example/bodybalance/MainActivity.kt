package com.example.bodybalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bodybalance.navigation.Navigation
import com.example.bodybalance.ui.theme.BodyBalanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodyBalanceTheme {
                Navigation()
            }
        }
    }
}