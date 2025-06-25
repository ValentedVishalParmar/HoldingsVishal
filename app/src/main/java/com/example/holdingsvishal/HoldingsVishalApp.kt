package com.example.holdingsvishal

import android.app.Application
import com.dataholding.vishal.data.remote.handler.NetworkHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

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

    companion object;

    override fun onCreate() {
        super.onCreate()
        // Network monitoring is automatically started by NetworkHandler when needed
        // No additional initialization required
    }

    override fun onTerminate() {
        super.onTerminate()
        // Clear all pending network requests when app is terminated
        networkHandler.clearPendingRequests()
    }
}