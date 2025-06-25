package com.dataholding.vishal.core.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

fun <T> Context.navigateTo(destinationScreen: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, destinationScreen)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Context.finishAndNavigateTo(destinationScreen: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, destinationScreen)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
    (this as Activity).finish()
}

fun getDataFromIntent(intent: Intent?, data: String): String? {
    return intent?.extras?.getString(data)
}


fun AppCompatActivity.handleOnBackPressed(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed.invoke()
        }
    })
}

fun Activity.disableClick(view: View?) {

    runOnUiThread {
        view?.isClickable = false
        Handler(Looper.getMainLooper()).postDelayed({
            if (this.isFinishing.not()) {
                view?.isClickable = true
            }
        }, 1000)
    }

}

fun Double?.formattedAmount(): String {
    val otherSymbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#######.##", otherSymbols)
    return decimalFormat.format(this).toString()

}

val Context.isNetworkConnected: Boolean
    get() {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.getNetworkCapabilities(manager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || it.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_ETHERNET
            ) || it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } == true
    }

fun Activity.toast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
