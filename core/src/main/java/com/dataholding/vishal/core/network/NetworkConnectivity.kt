package com.dataholding.vishal.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Network connectivity utility for checking internet availability and monitoring network state changes.
 *
 * This class provides:
 * - Real-time internet connectivity checking
 * - Network state change monitoring with callbacks
 * - Flow-based network state observation
 *
 * Usage:
 * Used to check if the device has internet connectivity before making API calls
 * and to monitor network state changes for automatic retry mechanisms.
 *
 * @property context The application context for accessing connectivity services.
 * @constructor Injects the application context for dependency injection.
 */
@Singleton
class NetworkConnectivity @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private var networkCallback: ((Boolean) -> Unit)? = null
    private var isMonitoring = false
    
    /**
     * Checks if the device currently has internet connectivity.
     *
     * @return true if internet is available, false otherwise.
     *
     * Usage:
     * val isConnected = networkConnectivity.isInternetAvailable()
     */
    fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
    
    /**
     * Starts monitoring network state changes with a callback.
     *
     * @param callback The callback to be invoked when network state changes.
     *                Parameter is true when network is available, false when lost.
     *
     * Usage:
     * networkConnectivity.startMonitoring { isConnected ->
     *     if (isConnected) {
     *         // Network restored, retry pending requests
     *     }
     * }
     */
    fun startMonitoring(callback: (Boolean) -> Unit) {
        if (isMonitoring) return
        
        networkCallback = callback
        isMonitoring = true
        
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                this@NetworkConnectivity.networkCallback?.invoke(true)
            }
            
            override fun onLost(network: Network) {
                super.onLost(network)
                this@NetworkConnectivity.networkCallback?.invoke(false)
            }
        }
        
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        
        // Send initial state
        val initialState = isInternetAvailable()
        this.networkCallback?.invoke(initialState)
    }
    
    /**
     * Stops monitoring network state changes.
     */
    fun stopMonitoring() {
        isMonitoring = false
        networkCallback = null
    }
    
    /**
     * Observes network connectivity changes as a Flow (for reactive programming).
     *
     * @return Flow of boolean values indicating network connectivity status.
     *
     * Usage:
     * networkConnectivity.observeNetworkState().collect { isConnected -> ... }
     */
    fun observeNetworkState(): Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }
            
            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }
        
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(networkRequest, callback)
        
        // Send initial state
        trySend(isInternetAvailable())
        
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
} 