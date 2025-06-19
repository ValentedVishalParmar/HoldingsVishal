package com.dataholding.vishal.coreui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.util.Symbol
import com.dataholding.vishal.coreui.widgets.TextSmallTitle
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfitLossBottomSheet(
    currentValue: String = "27893.65",
    totalInvestmentValue: String = "28590.71",
    todayProfitLoss: String = "235.65",
    profitAndLoss: String = "697.06(2.44%)",
    showBottomSheet: MutableState<Boolean> = mutableStateOf(false),
    onDismissBottomSheet: (showBottomSheet: Boolean) -> Unit = {}
) {

    // Screen content
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet.value = true
                }
            )
        }
    ) { contentPadding ->
        // Screen content
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
                        text = Symbol.SYM_RUPEES.plus(currentValue),
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
                        text = Symbol.SYM_RUPEES.plus(totalInvestmentValue),
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
                        text = Symbol.SYM_RUPEES.plus(todayProfitLoss),
                        textAlign = TextAlign.Start,
                    )
                }

                // 4] DIVIDER
                HorizontalDivider(modifier = Modifier.height(1.dp))

                // 5] TOTAL PROFIT OR LOSS
                ProfitAndLossRow(profitAndLoss, isFromBottomSheet = true) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                }
            }
        }
    }

}

@Preview(name = "ProfitLossBottomSheet", showBackground = true)
@Composable
fun ProfitLossBottomSheetP() {
    ProfitLossBottomSheet()
}