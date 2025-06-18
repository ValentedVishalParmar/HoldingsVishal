package com.dataholding.vishal.coreui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.widgets.TextSmallTitle

@Composable
fun FullScreenError(errorMsg: String, @DrawableRes errorIconId: Int) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(errorIconId),
            contentDescription = null,
            modifier = Modifier.size(34.dp))

        TextSmallTitle(text = errorMsg, textAlign = TextAlign.Center)

    }
}


@Composable
@Preview(name = "FullScreenErrorP", showBackground = true)
fun FullScreenErrorP() {

    FullScreenError("Error", R.drawable.ic_app_icon)
}


