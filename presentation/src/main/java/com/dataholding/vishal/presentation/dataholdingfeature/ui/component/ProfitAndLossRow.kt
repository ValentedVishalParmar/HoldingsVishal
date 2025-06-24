package com.dataholding.vishal.presentation.dataholdingfeature.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.core.R
import com.dataholding.vishal.core.util.Symbol

@Composable
fun ProfitAndLossRow(
    profitAndLoss: String?,
    isFromBottomSheet: Boolean = false,
    onClickExpand: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            //1. PROFIT AND LOSE
            Text(stringResource(R.string.profit_and_loss))

            //2. DROPDOWN ICON
            IconButton(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp), onClick = {
                    onClickExpand()
                }) {
                Icon(
                    imageVector = if (isFromBottomSheet) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                    contentDescription = null
                )
            }
        }
        // 3. VALUE OF PROFIT AND LOSE
        Text(
            color = if (profitAndLoss?.toDouble()
                    ?.let { it >= 0.0 } == true
            ) Color.Green else Color.Red,
            text = if (profitAndLoss?.toDouble()?.let { it >= 0.0 } == true) Symbol.SYM_RUPEES.plus(
                profitAndLoss
            ) else Symbol.SYM_RUPEES_MINUS.plus(profitAndLoss),
            textAlign = TextAlign.Start,
        )
    }
}

@Preview(showBackground = true, name = "ProfitAndLossRowP")
@Composable
fun ProfitAndLossRowP() {
    ProfitAndLossRow(onClickExpand = {

    }, profitAndLoss = "127.00")
}
