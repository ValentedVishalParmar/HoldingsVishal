package com.example.holdingsvishal.ui.widget

import ads_mobile_sdk.h1
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.holdingsvishal.R

@Composable
fun CircleImageView(imageId: Int) {
    Image(
        painter = painterResource(imageId),
        contentDescription = "Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape) // clip to the circle shape
            .border(5.dp, Color.Gray, CircleShape)//optional
    )
}

@Composable
fun TextLargeTitle(
    textAlign: TextAlign,
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        textAlign = textAlign,
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
fun TextSmallTiTle(
    textAlign: TextAlign,
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        textAlign = textAlign,
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
    )
}
