package com.dataholding.vishal.coreui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.util.Symbol
import com.dataholding.vishal.coreui.widgets.TextLargeTitleBold
import com.dataholding.vishal.coreui.widgets.TextSmallTitle

@Composable
fun HoldingValueItem(
    modifier: Modifier = Modifier,
    dataName: String,
    ltpValue: String = "119.10",
    netQty: String = "3",
    plValue: String = "1517.46",
    isProfit: Boolean = true,
    onItemClick:()->Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colorResource(R.color.white))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            // 1. HOLDING NAME
            TextLargeTitleBold(textAlign = TextAlign.Start, dataName)

            // 2.LTP VALUE VIEWS
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.ltp_value), color = Color.Gray)
                TextSmallTitle(text = Symbol.SYM_RUPEES.plus(ltpValue), textAlign = TextAlign.Start)
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            //3. NET QTY
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.net_quantity), color = Color.Gray)
                TextSmallTitle(text = netQty, textAlign = TextAlign.Start)
            }

            // 4. P&L VIEWS
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.pl_value), color = Color.Gray)
                Text(
                    text = if (isProfit) Symbol.SYM_RUPEES.plus(plValue) else Symbol.SYM_RUPEES_MINUS.plus(
                        plValue
                    ),
                    textAlign = TextAlign.Start,
                    color = if (isProfit) Color.Green else Color.Red
                )
            }

        }

        //5. DIVIDER
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

    }
}

@Preview(name = "HoldingValueItemP", showBackground = true)
@Composable
fun HoldingValueItemP() {
    HoldingValueItem(dataName = "ASHOKLEY")
}

@Preview(name = "HoldingValueItemL", showBackground = true)
@Composable
fun HoldingValueItemL() {
    HoldingValueItem(dataName = "ICICI", isProfit = false)
}
