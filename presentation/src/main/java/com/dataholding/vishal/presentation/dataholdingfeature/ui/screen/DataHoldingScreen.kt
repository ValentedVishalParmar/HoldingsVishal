package com.dataholding.vishal.presentation.dataholdingfeature.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.core.R
import com.dataholding.vishal.core.error.getError
import com.dataholding.vishal.presentation.dataholdingfeature.ui.component.HoldingValueItem
import com.dataholding.vishal.presentation.dataholdingfeature.ui.component.ProfitAndLossCardView
import com.dataholding.vishal.presentation.dataholdingfeature.ui.component.ProfitAndLossRow
import com.dataholding.vishal.core.ui.screen.FullScreenError
import com.dataholding.vishal.core.ui.screen.NetworkErrorScreen
import com.dataholding.vishal.core.ui.screen.LinearFullScreenProgress
import com.dataholding.vishal.core.ui.widgets.TextSmallTitle
import com.dataholding.vishal.core.util.formattedAmount
import com.dataholding.vishal.core.util.Symbol
import com.dataholding.vishal.presentation.dataholdingfeature.mvi.DataHoldingContract
import kotlinx.coroutines.launch

// todo:: 24 add the screen

@Composable
fun DataHoldingScreen(
    state: DataHoldingContract.DataHoldingState,
    showBottomSheet: MutableState<Boolean>,
    dispatch: (DataHoldingContract.DataHoldingEvent) -> Unit
) {
    when (state) {
        is DataHoldingContract.DataHoldingState.Error -> {
            Log.d("DataHoldingScreen>>>", "DataHoldingScreen: ${state.error.getError()}")
            if (state.error.getError()?.first?.let { id-> id==0 } == true) {
                FullScreenError( state.error.getError()?.second!!, R.drawable.ic_app_icon)

            } else {
                FullScreenError(stringResource(state.error.getError()?.first!!, state.error.getError()?.second!!), R.drawable.ic_app_icon)
            }

        }
        
        is DataHoldingContract.DataHoldingState.NetworkError -> {
            NetworkErrorScreen(
                onRetryClick = { dispatch(DataHoldingContract.DataHoldingEvent.RetryDataLoad) },
                errorMsg = state.message
            )
        }

        DataHoldingContract.DataHoldingState.Loading -> {
            val desc= stringResource(R.string.loading)
            LinearFullScreenProgress(modifier = Modifier.semantics {
                contentDescription = desc
            })
        }

        is DataHoldingContract.DataHoldingState.Success -> {
            DataHoldingScreenUI(state = state, showBottomSheet = showBottomSheet, dispatch)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataHoldingScreenUI(
    state: DataHoldingContract.DataHoldingState.Success,
    showBottomSheet: MutableState<Boolean>,
    dispatch: (DataHoldingContract.DataHoldingEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {

                //1] CURRENT VALUE
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.current_value))
                    TextSmallTitle(
                        text = Symbol.SYM_RUPEES.plus(state?.dataHoldingList?.get(0)?.currentValue.formattedAmount()),
                        textAlign = TextAlign.Start
                    )
                }

                // 2] TOTAL INVESTMENT

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.total_investment))
                    Text(
                        text = Symbol.SYM_RUPEES.plus(state?.dataHoldingList?.get(0)?.totalInvestmentValue.formattedAmount()),
                        textAlign = TextAlign.Start,
                    )
                }

                // 3] TODAY'S PROFIT AND LOSS

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.today_profit_loss))
                    Text(
                        text = Symbol.SYM_RUPEES.plus(state?.dataHoldingList?.get(0)?.todayProfitLoss.formattedAmount()),
                        textAlign = TextAlign.Start,
                    )
                }

                // 4] DIVIDER
                HorizontalDivider(modifier = Modifier.height(1.dp))

                // 5] TOTAL PROFIT OR LOSS
                ProfitAndLossRow(state.dataHoldingList?.get(0)?.totalProfitAndLoss.formattedAmount(), isFromBottomSheet = true) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                }
            }
        }

        //1] HOLDING DATA LIST
        LazyColumn(
            modifier = Modifier.padding(
                top = 0.dp,
                bottom = 38.dp,
                start = 0.dp,
                end = 0.dp
            )
        ) {
            itemsIndexed(
                state.dataHoldingList ?: listOf(),
            ) { index, data ->
                data?.let { newData ->
                    with(newData) {
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
                            plValue = todayProfitLoss.toString(),
                            isProfit = todayProfitLoss.toInt() >= 0,
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

        //2] LAST ITEM PROFIT AND LOSE VIEW
        if (!showBottomSheet.value) {
            ProfitAndLossCardView(state.dataHoldingList?.get(0)?.totalProfitAndLoss.formattedAmount(), onClickedExpandCollapsed = {
                showBottomSheet.value = true
            })
        }
    }
}
