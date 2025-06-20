package com.dataholding.vishal.coreui.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfitAndLossCardView(profitAndLose: String?, onClickedExpandCollapsed: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ) {
        ProfitAndLossRow(profitAndLoss = profitAndLose) {
            onClickedExpandCollapsed()
        }
    }
}


@Preview(showBackground = true, name = "ProfitAndLossCardViewP")
@Composable
fun ProfitAndLossCardViewP() {
    ProfitAndLossCardView("123.33") {}
}