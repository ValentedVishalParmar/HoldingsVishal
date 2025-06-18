package com.dataholding.vishal.presentation.dataholdingfeature.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.core.error.getError
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.components.FullScreenError
import com.dataholding.vishal.coreui.components.HoldingValueItem
import com.dataholding.vishal.coreui.components.LinearFullScreenProgress
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
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(
            state.dataHoldingList ?: listOf(),

        ) {
            HoldingValueItem(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable {
                        dispatch.invoke(
                            DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked(
                                it
                            )
                        )
                    },
                dataName = it?.symbol?.toString() ?: "-",
                ltpValue = it?.ltp?.toString() ?: "-",
                netQty = it?.quantity?.toString() ?: "-",
                plValue = it?.close?.toString() ?: "-",
                isProfit = false,
                onItemClick = {
                    dispatch(DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked(it))
                },
            )
        }
    }

}
