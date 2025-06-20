package com.example.holdingsvishal.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.holdingsvishal.navigation.Funds
import com.example.holdingsvishal.navigation.Invest
import com.example.holdingsvishal.navigation.Orders
import com.example.holdingsvishal.navigation.Portfolio
import com.example.holdingsvishal.navigation.Watchlist
import com.example.holdingsvishal.ui.screen.FundScreen
import com.example.holdingsvishal.ui.screen.InvestScreen
import com.example.holdingsvishal.ui.screen.OrderScreen
import com.example.holdingsvishal.ui.screen.PortfolioScreen
import com.example.holdingsvishal.ui.screen.WatchListScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = Portfolio,
        modifier = Modifier.padding(paddingValues = padding)
    ) {
        composable<Watchlist> {
            WatchListScreen()
        }
        composable<Orders> {
            OrderScreen()
        }
        composable<Portfolio> {
            PortfolioScreen()
        }
        composable<Funds> {
            FundScreen()
        }
        composable<Invest> {
            InvestScreen()
        }
    }
}
