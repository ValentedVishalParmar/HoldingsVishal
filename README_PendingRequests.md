# Pending Network Requests Management

## Overview

The `NetworkHandler` provides methods to manage pending network requests that are waiting for network connectivity to be restored. This document explains when and where to use these methods.

## Available Methods

### 1. `clearPendingRequests()`
Clears all pending network requests from the internal queue.

### 2. `getPendingRequestCount()`
Returns the number of pending requests waiting for network restoration.

## When to Use These Methods

### 🧹 **clearPendingRequests() - When to Call:**

#### 1. **Application Lifecycle Events**
```kotlin
// In Application class
override fun onTerminate() {
    super.onTerminate()
    networkHandler.clearPendingRequests() // Clear when app terminates
}
```

#### 2. **ViewModel Cleanup**
```kotlin
// In ViewModel
override fun onCleared() {
    super.onCleared()
    networkHandler.clearPendingRequests() // Clear when ViewModel is destroyed
}
```

#### 3. **User Logout/Authentication Changes**
```kotlin
// In AuthViewModel or UserManager
fun logout() {
    // Clear user data
    userRepository.clearUserData()
    
    // Clear pending network requests
    networkHandler.clearPendingRequests()
    
    // Navigate to login
    navigateToLogin()
}
```

#### 4. **App Goes to Background**
```kotlin
// In MainActivity or Application
override fun onPause() {
    super.onPause()
    if (isFinishing) {
        networkHandler.clearPendingRequests() // Clear when app is closing
    }
}
```

#### 5. **Network Configuration Changes**
```kotlin
// When switching between WiFi/Cellular or VPN
fun onNetworkConfigurationChanged() {
    networkHandler.clearPendingRequests() // Clear old pending requests
    // Re-initialize network settings
}
```

### 📊 **getPendingRequestCount() - When to Call:**

#### 1. **UI Indicators**
```kotlin
// In ViewModel
fun getPendingRequestsCount(): Int {
    return networkHandler.getPendingRequestCount()
}

// In UI
@Composable
fun NetworkStatusIndicator() {
    val pendingCount = viewModel.getPendingRequestsCount()
    
    if (pendingCount > 0) {
        Text("$pendingCount requests waiting for network")
    }
}
```

#### 2. **Debugging and Logging**
```kotlin
// In debug builds
fun logNetworkStatus() {
    val pendingCount = networkHandler.getPendingRequestCount()
    Log.d("Network", "Pending requests: $pendingCount")
}
```

#### 3. **Analytics and Monitoring**
```kotlin
// Track network issues
fun trackNetworkIssues() {
    val pendingCount = networkHandler.getPendingRequestCount()
    if (pendingCount > 0) {
        analytics.track("network_pending_requests", pendingCount)
    }
}
```

#### 4. **User Notifications**
```kotlin
// Show user-friendly messages
fun getNetworkStatusMessage(): String {
    val count = networkHandler.getPendingRequestCount()
    return when (count) {
        0 -> "Network is working properly"
        1 -> "1 request waiting for network"
        else -> "$count requests waiting for network"
    }
}
```

## Implementation Examples

### Example 1: ViewModel with Pending Request Monitoring
```kotlin
@HiltViewModel
class DataHoldingViewModel @Inject constructor(
    private val dataUseCase: HoldingDataUseCase,
    private val networkHandler: NetworkHandler
) : ViewModel(), DataHoldingContract {

    // Monitor pending requests
    fun getPendingRequestCount(): Int {
        return networkHandler.getPendingRequestCount()
    }

    // Cleanup on ViewModel destruction
    override fun onCleared() {
        super.onCleared()
        networkHandler.clearPendingRequests()
    }
}
```

### Example 2: Application-Level Management
```kotlin
@HiltAndroidApp
class HoldingsVishalApp : Application() {

    @Inject
    lateinit var networkHandler: NetworkHandler

    override fun onTerminate() {
        super.onTerminate()
        networkHandler.clearPendingRequests()
    }
}
```

### Example 3: Network Status UI Component
```kotlin
@Composable
fun NetworkStatusBar(viewModel: DataHoldingViewModel) {
    val pendingCount = viewModel.getPendingRequestCount()
    
    if (pendingCount > 0) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Yellow.copy(alpha = 0.8f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$pendingCount requests waiting for network",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    onClick = { /* Retry logic */ }
                ) {
                    Text("Retry")
                }
            }
        }
    }
}
```

### Example 4: Network Request Monitor Utility
```kotlin
@Singleton
class NetworkRequestMonitor @Inject constructor(
    private val networkHandler: NetworkHandler
) {
    fun getPendingRequestCount(): Int = networkHandler.getPendingRequestCount()
    
    fun clearPendingRequests() = networkHandler.clearPendingRequests()
    
    fun hasPendingRequests(): Boolean = getPendingRequestCount() > 0
    
    fun getPendingRequestsMessage(): String {
        val count = getPendingRequestCount()
        return when (count) {
            0 -> "No pending requests"
            1 -> "1 request waiting for network"
            else -> "$count requests waiting for network"
        }
    }
}
```

## Best Practices

### ✅ **Do:**
- Clear pending requests when user logs out
- Clear pending requests when app terminates
- Monitor pending requests for UI feedback
- Use pending request count for debugging
- Clear pending requests on network configuration changes

### ❌ **Don't:**
- Clear pending requests on every API call
- Clear pending requests during normal app operation
- Ignore pending request count in production
- Clear pending requests without user consent

## Integration Points

### 1. **Activity/Fragment Lifecycle**
```kotlin
override fun onDestroy() {
    super.onDestroy()
    if (isFinishing) {
        networkHandler.clearPendingRequests()
    }
}
```

### 2. **User Authentication**
```kotlin
fun onUserLogout() {
    networkHandler.clearPendingRequests()
    // Other logout logic
}
```

### 3. **Network State Changes**
```kotlin
fun onNetworkStateChanged(isConnected: Boolean) {
    if (!isConnected) {
        // Network lost - pending requests will be created automatically
    } else {
        // Network restored - pending requests will be executed automatically
        // Optionally clear if needed
        // networkHandler.clearPendingRequests()
    }
}
```

### 4. **App Background/Foreground**
```kotlin
override fun onStop() {
    super.onStop()
    if (isFinishing) {
        networkHandler.clearPendingRequests()
    }
}
```

This comprehensive approach ensures proper management of pending network requests throughout the application lifecycle. 