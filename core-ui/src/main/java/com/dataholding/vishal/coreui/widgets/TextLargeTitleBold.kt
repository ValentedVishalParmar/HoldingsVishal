package com.dataholding.vishal.coreui.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextLargeTitleBold(
    textAlign: TextAlign,
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        textAlign = textAlign,
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
}