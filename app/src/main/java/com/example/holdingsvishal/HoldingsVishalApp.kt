package com.example.holdingsvishal

import android.app.Application
import com.dataholding.vishal.data.remote.handler.NetworkHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.dataholding.vishal.core.util.SecurityUtils
import android.util.Log

/**
 * Application class for the Holdings Vishal app.
 *
 * Usage:
 * Main entry point for the application, initializes app-wide configurations.
 * Network monitoring is handled internally by the NetworkHandler when needed.
 *
 * @property networkHandler The network handler for global network request management.
 * @constructor Injects dependencies for dependency injection.
 */
@HiltAndroidApp
class HoldingsVishalApp : Application() {

    @Inject
    lateinit var networkHandler: NetworkHandler

    override fun onCreate() {
        super.onCreate()
        checkForSecurityConcerns()
    }

    private fun checkForSecurityConcerns() {
        if (SecurityUtils.isDebuggerAttached()) {
            Log.w("Security", "Debugger is attached! Possible reverse engineering attempt.")
        }
        if (SecurityUtils.isDeviceRooted()) {
            Log.w("Security", "Device is rooted! Increased risk of tampering.")
        }
        if (SecurityUtils.isAppTampered(this)) {
            Log.w("Security", "App signature does not match! Possible tampering detected.")
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        networkHandler.clearPendingRequests()
    }
}