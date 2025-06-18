package com.example.holdingsvishal.ui.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
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
import com.dataholding.vishal.coreui.components.TopAppBarWithSearch
import com.dataholding.vishal.presentation.dataholdingfeature.mvi.DataHoldingContract
import com.dataholding.vishal.presentation.dataholdingfeature.screen.DataHoldingScreen
import com.dataholding.vishal.presentation.dataholdingfeature.viewmodel.DataHoldingViewModel
import com.example.holdingsvishal.navigation.DataHolding
import com.example.holdingsvishal.ui.theme.HoldingsVishalTheme
import com.example.holdingsvishal.util.TopAppBarState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HoldingsVishalTheme {
                DataHoldingApp()
            }
        }
    }
}

@Composable
fun DataHoldingApp() {
    val navController = rememberNavController()
    val topAppBarState = rememberSaveable { mutableStateOf(TopAppBarState.Portfolio) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithSearch(
                title = topAppBarState.value.name,
                onMenuClick = {

                })
        },
        bottomBar = {
            NavigationBar() { }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DataHolding,
            modifier = Modifier.padding(innerPadding)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HoldingsVishalTheme {
        DataHoldingApp()
    }
}