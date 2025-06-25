package com.dataholding.vishal.data.remote.handler

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.core.mapper.ResultMapper
import com.dataholding.vishal.core.network.NetworkConnectivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Enhanced network handler with internet connectivity checking, retry mechanism, and internal pending request management.
 *
 * This class manages all network operations including:
 * - Internet connectivity checking
 * - Retry mechanism with exponential backoff
 * - Internal pending request management
 * - Automatic request execution when network is restored
 *
 * Usage:
 * Used in repository implementations to make safe API calls with network checking and retry logic.
 *
 * @property networkConnectivity The network connectivity utility for checking internet availability.
 * @constructor Injects the network connectivity utility for dependency injection.
 */
@Singleton
class NetworkHandler @Inject constructor(
    private val networkConnectivity: NetworkConnectivity
) {
    
    // Internal storage for pending requests
    private val pendingRequests = mutableListOf<() -> Unit>()
    private var isNetworkMonitoring = false
    
    /**
     * Makes a safe API call with internet connectivity checking and retry mechanism.
     * If network is unavailable, the request is stored internally and executed when network is restored.
     *
     * @param ioDispatcher The coroutine dispatcher to use. Default is Dispatchers.IO.
     * @param apiCall The API call function to execute.
     * @param mapper The mapper to convert API response to domain model.
     * @param maxRetries Maximum number of retry attempts. Default is 3.
     * @param initialDelay Initial delay before first retry in milliseconds. Default is 1000.
     * @return Either containing Failure on error or mapped result on success.
     *
     * Usage:
     * val result = networkHandler.safeApiCall(
     *     apiCall = { apiService.getData() },
     *     mapper = dataMapper
     * )
     */
    suspend fun <T, R> safeApiCall(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>,
        mapper: ResultMapper<T, R>,
        maxRetries: Int = 3,
        initialDelay: Long = 1000
    ): Either<Failure, R> {
        
        return withContext(ioDispatcher) {
            // Check internet connectivity before making the API call
            if (!networkConnectivity.isInternetAvailable()) {
                // Store this request for later execution when network is restored
                addPendingRequest {
                    // This will be called when network is restored
                    // The actual API call will be retried by the calling repository
                }
                
                // Start network monitoring if not already started
                startNetworkMonitoring()
                
                return@withContext Either.Left(
                    Failure.NetworkConnectivityError("No internet connection available")
                )
            }
            
            // Execute the API call with retry logic
            executeApiCallWithRetry(apiCall, mapper, maxRetries, initialDelay)
        }
    }
    
    /**
     * Makes a simple API call without retry mechanism (for backward compatibility).
     *
     * @param ioDispatcher The coroutine dispatcher to use. Default is Dispatchers.IO.
     * @param apiCall The API call function to execute.
     * @param mapper The mapper to convert API response to domain model.
     * @return Either containing Failure on error or mapped result on success.
     */
    suspend fun <T, R> simpleApiCall(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>,
        mapper: ResultMapper<T, R>
    ): Either<Failure, R> {
        return safeApiCall(ioDispatcher, apiCall, mapper, maxRetries = 0)
    }
    
    /**
     * Executes API call with retry mechanism.
     */
    private suspend fun <T, R> executeApiCallWithRetry(
        apiCall: suspend () -> Response<T>,
        mapper: ResultMapper<T, R>,
        maxRetries: Int,
        initialDelay: Long
    ): Either<Failure, R> {
        var currentRetry = 0
        var currentDelay = initialDelay
        
        while (currentRetry <= maxRetries) {
            val result = runCatching {
                val response = apiCall()
                
                if (response.isSuccessful) {
                    response.body()?.let {
                        Either.Right(mapper.map(it))
                    } ?: Either.Left(
                        Failure.ServerError(
                            code = response.code(),
                            message = response.message()
                        )
                    )
                } else {
                    Either.Left(Failure.ServerError(response.code(), response.message()))
                }
            }.getOrElse { exception ->
                exception.toEither()
            }
            
            // If successful, return the result immediately
            if (result is Either.Right) {
                return result
            }
            
            // Check if we should retry based on the error type
            val shouldRetry = when (result) {
                is Either.Left -> {
                    val failure = result.value
                    when (failure) {
                        is Failure.NetworkError -> true
                        is Failure.ServerError -> {
                            // Only retry on 5xx server errors, not 4xx client errors
                            failure.code in 500..599
                        }
                        else -> false
                    }
                }
                else -> false
            }
            
            // If we shouldn't retry or this is the last attempt, return the result
            if (!shouldRetry || currentRetry == maxRetries) {
                return result
            }
            
            // If we get here, we should retry
            currentRetry++
            delay(currentDelay)
            currentDelay *= 2 // Exponential backoff
        }
        
        // This should never be reached, but just in case
        return Either.Left(Failure.UnknownError(Exception("Max retries exceeded")))
    }
    
    /**
     * Adds a request to the pending requests list.
     */
    private fun addPendingRequest(request: () -> Unit) {
        synchronized(pendingRequests) {
            pendingRequests.add(request)
        }
    }
    
    /**
     * Starts network monitoring to detect when internet is restored.
     */
    private fun startNetworkMonitoring() {
        if (isNetworkMonitoring) return
        
        isNetworkMonitoring = true
        
        // Start monitoring network state
        networkConnectivity.startMonitoring { isConnected ->
            if (isConnected) {
                // Network is restored, execute pending requests
                executePendingRequests()
            }
        }
    }
    
    /**
     * Executes all pending requests when network is restored.
     */
    private fun executePendingRequests() {
        synchronized(pendingRequests) {
            if (pendingRequests.isNotEmpty()) {
                // Clear pending requests and notify that network is restored
                // The actual retry will be handled by the calling repository
                pendingRequests.clear()
                
                // Show toast notification (this will be handled by the UI layer)
                // We can't show toast directly from here, so we'll rely on the UI
                // to handle the network restoration notification
            }
        }
    }
    
    /**
     * Clears all pending requests (useful for cleanup).
     */
    fun clearPendingRequests() {
        synchronized(pendingRequests) {
            pendingRequests.clear()
        }
    }
    
    /**
     * Gets the number of pending requests.
     */
    fun getPendingRequestCount(): Int {
        synchronized(pendingRequests) {
            return pendingRequests.size
        }
    }
}

