package com.example.holdingsvishal.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dataholding.vishal.presentation.dataholdingfeature.mvi.DataHoldingContract
import com.dataholding.vishal.presentation.dataholdingfeature.screen.DataHoldingScreen
import com.dataholding.vishal.presentation.dataholdingfeature.viewmodel.DataHoldingViewModel
import com.example.holdingsvishal.navigation.DataHolding
import com.example.holdingsvishal.ui.component.TopAppBarWithSearch
import com.example.holdingsvishal.util.TopAppBarState

@Composable
fun PortfolioScreen() {
    val navController = rememberNavController()
    val topAppBarState =
        rememberSaveable { mutableStateOf(TopAppBarState.Portfolio) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithSearch(
                title = topAppBarState.value.name,
                onMenuClick = {

                })
        },

        ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DataHolding,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<DataHolding> {
                val viewModel: DataHoldingViewModel = hiltViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                val dispatch: (DataHoldingContract.DataHoldingEvent) -> Unit = { event ->
                    viewModel.event(event)
                }

                LaunchedEffect(key1 = Unit) {
                    viewModel.effect.collect { effect ->
                        when (effect) {
                            is DataHoldingContract.DataHoldingEffect.NavigateToDataHoldingDetailsDetails ->
                                with(effect) {
                                    Log.d("DATA>>>", "DataHoldingApp: event")
                                }
                        }

                    }
                }
                DataHoldingScreen(state = state.value, dispatch = dispatch)
            }

        }

    }
}

@Composable
@Preview(showBackground = true, name = "PortfolioScreenP")
fun PortfolioScreenP() {
    PortfolioScreen()
}


