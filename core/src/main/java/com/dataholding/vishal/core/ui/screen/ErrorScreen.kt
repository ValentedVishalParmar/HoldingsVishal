package com.dataholding.vishal.core.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.core.R
import com.dataholding.vishal.core.ui.widgets.TextSmallTitle

/**
 * Full screen error component with optional retry functionality.
 *
 * @param errorMsg The error message to display.
 * @param errorIconId The drawable resource ID for the error icon. Default is ic_error.
 * @param showRetryButton Whether to show a retry button. Default is false.
 * @param onRetryClick Callback function when retry button is clicked. Default is null.
 * @param retryButtonText The text for the retry button. Default is "Retry".
 */
@Composable
fun FullScreenError(
    errorMsg: String, 
    @DrawableRes errorIconId: Int = R.drawable.ic_error,
    showRetryButton: Boolean = false,
    onRetryClick: (() -> Unit)? = null,
    retryButtonText: String = "Retry"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(errorIconId),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextSmallTitle(
            text = errorMsg, 
            textAlign = TextAlign.Center
        )
        
        if (showRetryButton && onRetryClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onRetryClick,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(text = retryButtonText)
            }
        }
    }
}

/**
 * Network connectivity error screen with retry functionality.
 *
 * @param onRetryClick Callback function when retry button is clicked.
 * @param errorMsg Custom error message. Default is "No internet connection".
 */
@Composable
fun NetworkErrorScreen(
    onRetryClick: () -> Unit,
    errorMsg: String = "No internet connection"
) {
    FullScreenError(
        errorMsg = errorMsg,
        errorIconId = R.drawable.ic_error,
        showRetryButton = true,
        onRetryClick = onRetryClick,
        retryButtonText = "Retry"
    )
}

@Composable
@Preview(name = "FullScreenErrorP", showBackground = true)
fun FullScreenErrorP() {
    FullScreenError("Error", R.drawable.ic_app_icon)
}

@Composable
@Preview(name = "NetworkErrorScreenP", showBackground = true)
fun NetworkErrorScreenP() {
    NetworkErrorScreen(onRetryClick = {})
}


