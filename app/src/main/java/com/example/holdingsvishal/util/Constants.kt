package com.example.holdingsvishal.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Replay
import com.example.holdingsvishal.navigation.BottomNavRouteModel
import com.example.holdingsvishal.navigation.*

object Constants {
     const val SPLASH_DISPLAY_DURATION = 2000L

     val topLevelRoutes = listOf(
          BottomNavRouteModel("Watchlist", Watchlist, Icons.Default.Menu),
          BottomNavRouteModel("Orders", Orders, Icons.Default.Replay),
          BottomNavRouteModel("Portfolio", Portfolio, Icons.Default.Elevator),
          BottomNavRouteModel("Funds", Funds, Icons.Default.CurrencyRupee),
          BottomNavRouteModel("Invest", Invest, Icons.Default.AccountTree),
     )
}


