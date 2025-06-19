package com.dataholding.vishal.presentation.dataholdingfeature.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.core.error.getError
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.screen.FullScreenError
import com.dataholding.vishal.coreui.screen.component.HoldingValueItem
import com.dataholding.vishal.coreui.screen.LinearFullScreenProgress
import com.dataholding.vishal.coreui.screen.component.ProfitAndLossCardView
import com.dataholding.vishal.presentation.dataholdingfeature.mvi.DataHoldingContract

// todo:: 24 add the screen

@Composable
fun DataHoldingScreen(
    state: DataHoldingContract.DataHoldingState,
    dispatch: (DataHoldingContract.DataHoldingEvent) -> Unit
) {
    when (state) {
        is DataHoldingContract.DataHoldingState.Error -> {
            Log.d("DataHoldingScreen>>>", "DataHoldingScreen: ${state.error.getError()}")
            FullScreenError(state.error.getError(), R.drawable.ic_app_icon)
        }

        DataHoldingContract.DataHoldingState.Loading -> {
            LinearFullScreenProgress(modifier = Modifier.semantics {
                contentDescription = "Loading"
            })
        }

        is DataHoldingContract.DataHoldingState.Success -> {
            DataHoldingScreenUI(state,dispatch)
        }
    }
}

@Composable
fun DataHoldingScreenUI(
    state: DataHoldingContract.DataHoldingState.Success,
    dispatch: (DataHoldingContract.DataHoldingEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.White), contentAlignment = Alignment.BottomCenter) {
            var profitAndLoss: Double? = 0.0
            var investment: Double? = 0.0

        //1] HOLDING DATA LIST
        LazyColumn(modifier = Modifier.padding(top = 0.dp, bottom = 38.dp, start = 0.dp, end = 0.dp)) {
            itemsIndexed(
                state.dataHoldingList ?: listOf(),
            ) { index, data ->
                data?.let { newData ->
                    with(newData) {

                        investment = avgPrice?.times(quantity ?: 1)
                        profitAndLoss = investment?.let { (ltp?.times(quantity?:1))?.minus(it) }

                        profitAndLoss?.toInt()?.let {
                            HoldingValueItem(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        dispatch.invoke(
                                            DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked(
                                                data
                                            )
                                        )
                                    },
                                dataName = symbol ?: "-",
                                ltpValue = ltp?.toString() ?: "-",
                                netQty = quantity?.toString() ?: "-",
                                plValue = profitAndLoss.toString(),
                                isProfit = it >= 0,
                                onItemClick = {
                                    dispatch(
                                        DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked(
                                            data
                                        )
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }


        //2] LAST ITEM PROFIT AND LOSE VIEW
        ProfitAndLossCardView(profitAndLoss?.toString(), onClickedExpandCollapsed = {

        })
    }
}
