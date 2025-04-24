package com.example.bodybalance.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bodybalance.core.composable.SetSystemBarsColor
import com.example.bodybalance.main.presentation.navigation.Navigation
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val isAuthenticated by viewModel.isAuthenticated.collectAsState()
            BodyBalanceTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    if (isAuthenticated != null) {
                        Navigation(isAuthenticated = isAuthenticated!!)
                    } else {
                        Unit
                    }
                }
            }
            SetSystemBarsColor()
        }
    }
}