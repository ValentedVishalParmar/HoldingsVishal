package com.example.holdingsvishal.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.holdingsvishal.ui.component.BottomNavigationBar
import com.example.holdingsvishal.ui.component.NavHostContainer
import com.example.holdingsvishal.ui.theme.HoldingsVishalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HoldingsVishalTheme {
                DataHoldingApp(rememberNavController())
            }
        }
    }
}

@Composable
fun DataHoldingApp(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHostContainer(navController = navController, padding = innerPadding)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HoldingsVishalTheme {
        DataHoldingApp(rememberNavController())
    }
}