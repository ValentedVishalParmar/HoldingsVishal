# NetworkHandler with Internal Pending Request Management

## Overview

The `NetworkHandler` class now manages all network operations internally, including pending request management, without requiring changes to ViewModels or other components. This follows the **KISS principle** by centralizing all network logic in one place.

## Key Features

### 1. **Self-Contained Pending Request Management**
- Pending requests are stored internally in the `NetworkHandler`
- No need to modify ViewModels or other components
- Automatic retry when network is restored

### 2. **Automatic Network Monitoring**
- Starts monitoring when first network connectivity error occurs
- Automatically detects when internet is restored
- Executes pending requests when network becomes available

### 3. **Retry Mechanism with Exponential Backoff**
- Configurable retry attempts (default: 3)
- Exponential backoff delay (default: 1000ms initial, doubles each retry)
- Only retries on network errors and 5xx server errors

## How It Works

### 1. **Normal API Call Flow**
```kotlin
// Repository implementation - no changes needed
class HoldingDataRepositoryImpl @Inject constructor(
    private val apiService: HoldingDataApiService,
    private val mapper: HoldingDataMapper,
    private val networkHandler: NetworkHandler
) : HoldingDataRepository {
    
    override suspend fun apiCallForGetHoldingData(): Either<Failure, List<HoldingData?>> {
        return networkHandler.safeApiCall(
            apiCall = { apiService.apiCallForGetHoldingData() },
            mapper = { mapper.map(it) }
        )
    }
}
```

### 2. **Network Connectivity Error Flow**
```kotlin
// When network is unavailable:
// 1. NetworkHandler checks connectivity
// 2. If no internet, stores request internally
// 3. Starts network monitoring automatically
// 4. Returns NetworkConnectivityError to repository
// 5. Repository returns error to ViewModel
// 6. ViewModel shows network error UI
```

### 3. **Network Restoration Flow**
```kotlin
// When network is restored:
// 1. NetworkHandler detects network restoration
// 2. Clears pending requests
// 3. User can manually retry via UI
// 4. New API call succeeds (network is now available)
```

## Benefits

### ✅ **KISS Principle**
- Single responsibility: NetworkHandler manages all network operations
- No complex interactions between multiple components
- Simple and easy to understand

### ✅ **No Breaking Changes**
- Repository implementations remain unchanged
- ViewModels remain unchanged
- UI components remain unchanged

### ✅ **Automatic Management**
- No manual pending request tracking needed
- No manual network monitoring setup required
- No cleanup code needed in ViewModels

### ✅ **Thread Safe**
- Uses synchronized blocks for pending request management
- Safe for concurrent access

## Usage Examples

### Basic Usage
```kotlin
// Repository - no changes needed
val result = networkHandler.safeApiCall(
    apiCall = { apiService.getData() },
    mapper = { dataMapper.map(it) }
)
```

### Custom Retry Configuration
```kotlin
val result = networkHandler.safeApiCall(
    apiCall = { apiService.getData() },
    mapper = { dataMapper.map(it) },
    maxRetries = 5,
    initialDelay = 2000
)
```

### Simple API Call (No Retry)
```kotlin
val result = networkHandler.simpleApiCall(
    apiCall = { apiService.getData() },
    mapper = { dataMapper.map(it) }
)
```

## Error Handling

The `NetworkHandler` returns appropriate `Failure` types:

- `Failure.NetworkConnectivityError`: No internet connection
- `Failure.NetworkError`: Network-related errors (timeout, connection issues)
- `Failure.ServerError`: HTTP errors (4xx, 5xx)
- `Failure.UnknownError`: Unexpected errors

## UI Integration

The UI only needs to handle the `NetworkConnectivityError` by showing a retry button:

```kotlin
when (failure) {
    is Failure.NetworkConnectivityError -> {
        // Show network error screen with retry button
        updateState(DataHoldingState.NetworkError(failure.message))
    }
    // ... other error types
}
```

When the user clicks retry, the same API call will succeed (if network is restored) because the `NetworkHandler` automatically manages the network state.

## Summary

This approach is much simpler and follows the KISS principle:

1. **Single Point of Control**: All network logic is in `NetworkHandler`
2. **No Dependencies**: Other components don't need to know about pending requests
3. **Automatic Management**: Network monitoring and retry logic is handled internally
4. **Clean Architecture**: Maintains separation of concerns
5. **Easy to Test**: Single component to test for network functionality 