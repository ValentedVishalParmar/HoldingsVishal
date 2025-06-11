package com.example.holdingsvishal.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.holdingsvishal.R
import com.example.holdingsvishal.ui.theme.HoldingsVishalTheme
import com.example.holdingsvishal.ui.widget.CircleImageView
import com.example.holdingsvishal.util.Constants.SPLASH_DISPLAY_DURATION
import com.example.holdingsvishal.util.navigateTo


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HoldingsVishalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }

            LaunchedEffect("okay") {
                showSplashScreen()
            }
        }

    }

    private fun showSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateTo(MainActivity::class.java)
        }, SPLASH_DISPLAY_DURATION)
    }

}

@Composable
fun SplashViews() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        val appName = LocalContext.current.getString(R.string.app_name)

        // SPLASH LOG
        //CircleImageView()


        // APP NAME
        Text(
            text = appName,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )

        // APP VERSION
        Text(
            text = "1.0.0",
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    HoldingsVishalTheme {
        SplashViews("Android")
    }
}