package com.example.holdingsvishal.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.widgets.CircleImageView
import com.dataholding.vishal.coreui.widgets.TextLargeTitle
import com.dataholding.vishal.coreui.widgets.TextSmallTitle
import com.dataholding.vishal.coreui.widgets.TextTitleMedium
import com.example.holdingsvishal.BuildConfig
import com.example.holdingsvishal.R
import com.example.holdingsvishal.ui.theme.HoldingsVishalTheme
import com.example.holdingsvishal.util.Constants.SPLASH_DISPLAY_DURATION
import com.example.holdingsvishal.util.finishAndNavigateTo
import com.example.holdingsvishal.util.navigateTo
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                showSplashScreen()
            }

            HoldingsVishalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashViews()
                }
            }
        }

    }

    private fun showSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            finishAndNavigateTo(MainActivity::class.java)
        }, SPLASH_DISPLAY_DURATION)
    }

}

@Composable
fun SplashViews() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0), true, reverseScrolling = false),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // String
       val appName =  LocalContext.current.getString(R.string.app_name)
        val appVersion = LocalContext.current.getString(R.string.app_version, BuildConfig.VERSION_NAME)

        // SPLASH LOGO
        CircleImageView(imageId = R.drawable.ic_app_icon)

        Spacer(Modifier.padding(120.dp))

        // APP NAME
        TextLargeTitle(
            textAlign = TextAlign.Center,
            text = appName,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.padding(16.dp))

        // APP VERSION
        TextSmallTitle(
            text = appVersion,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Spacer(Modifier.padding(16.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun SplashViewsPreview() {
    HoldingsVishalTheme {
        SplashViews()
    }
}