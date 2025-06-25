package com.dataholding.vishal.data.repository

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.data.local.datasource.HoldingDataLocalDataSource
import com.dataholding.vishal.data.mapper.HoldingDataMapper
import com.dataholding.vishal.data.remote.api.HoldingDataApiService
import com.dataholding.vishal.data.remote.handler.NetworkHandler
import com.dataholding.vishal.domain.model.HoldingData
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import com.dataholding.vishal.domain.usecase.HoldingDataBusinessLogic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [HoldingDataRepository] with offline-first functionality.
 *
 * Usage:
 * Provides holding data with fallback to local cache when network is unavailable.
 * Implements offline-first architecture with automatic caching.
 *
 * @property holdingDataApiService The API service for network requests.
 * @property holdingDataMapper The mapper for converting API responses to domain models.
 * @property holdingDataBusinessLogic The business logic class for calculations.
 * @property networkHandler The network handler for safe API calls with retry mechanism.
 * @property localDataSource The local data source for caching and offline access.
 * @constructor Injects dependencies for repository implementation.
 */
@Singleton
class HoldingDataRepositoryImpl @Inject constructor(
    private val holdingDataApiService: HoldingDataApiService,
    private val holdingDataMapper: HoldingDataMapper,
    private val holdingDataBusinessLogic: HoldingDataBusinessLogic,
    private val networkHandler: NetworkHandler,
    private val localDataSource: HoldingDataLocalDataSource
) : HoldingDataRepository {

    companion object {
        private const val DATA_FRESHNESS_THRESHOLD = 5 * 60 * 1000L // 5 minutes
    }

    /**
     * Fetches holding data with offline-first approach.
     * First tries to get fresh data from API, falls back to local cache if network fails.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     *
     * Usage:
     * Call this function from a use case to retrieve holding data with fallback support.
     */
    override suspend fun apiCallForGetHoldingData(): Either<Failure, List<HoldingData?>?> {
        // First, try to get fresh data from API
        val apiResult = networkHandler.safeApiCall(
            apiCall = { holdingDataApiService.apiCallForGetHoldingData() },
            mapper = { holdingDataMapper.map(it) }
        )

        return when (apiResult) {
            is Either.Right -> {
                // API call successful, cache the data and return
                apiResult.value?.let { holdingDataList ->
                    // Filter out null values and cache valid data
                    val validData = holdingDataList.filterNotNull()
                    if (validData.isNotEmpty()) {
                        localDataSource.saveHoldingData(validData)
                    }
                }
                apiResult
            }
            is Either.Left -> {
                // API call failed, try to get data from local cache
                val cachedData = localDataSource.getHoldingDataList()
                if (cachedData.isNotEmpty()) {
                    // Return cached data with a note that it's from cache
                    Either.Right(cachedData)
                } else {
                    // No cached data available, return the original failure
                    apiResult
                }
            }
        }
    }

    /**
     * Gets holding data as a Flow for reactive updates.
     * Provides real-time updates from both API and local cache.
     *
     * @return Flow of holding data list.
     */
    override fun getHoldingDataFlow(): Flow<List<HoldingData>> {
        return localDataSource.getHoldingData()
    }

    /**
     * Gets holding data by symbol from local cache.
     *
     * @param symbol Stock symbol.
     * @return Flow of holding data for the symbol.
     */
    override fun getHoldingDataBySymbol(symbol: String): Flow<HoldingData?> {
        return localDataSource.getHoldingDataBySymbol(symbol)
    }

    /**
     * Refreshes data from API and updates local cache.
     * Ignores data freshness check and forces a refresh.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    override suspend fun refreshHoldingData(): Either<Failure, List<HoldingData?>?> {
        return apiCallForGetHoldingData()
    }

    /**
     * Gets holding data with smart caching.
     * Uses cached data if it's fresh enough, otherwise fetches from API.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    override suspend fun getHoldingDataWithSmartCache(): Either<Failure, List<HoldingData?>?> {
        // Check if cached data is fresh enough
        val isDataStale = localDataSource.isDataStale(DATA_FRESHNESS_THRESHOLD)
        
        if (!isDataStale) {
            // Use cached data if it's fresh
            val cachedData = localDataSource.getHoldingDataList()
            if (cachedData.isNotEmpty()) {
                return Either.Right(cachedData)
            }
        }

        // Data is stale or doesn't exist, fetch from API
        return apiCallForGetHoldingData()
    }

    /**
     * Clears all cached holding data.
     */
    override suspend fun clearCachedData() {
        localDataSource.clearHoldingData()
    }

    /**
     * Gets the count of cached holding data records.
     *
     * @return Number of cached records.
     */
    override suspend fun getCachedDataCount(): Int {
        return localDataSource.getHoldingDataCount()
    }

    /**
     * Checks if cached data exists and is fresh.
     *
     * @return True if fresh cached data is available.
     */
    override suspend fun hasFreshCachedData(): Boolean {
        return !localDataSource.isDataStale(DATA_FRESHNESS_THRESHOLD) && 
               localDataSource.getHoldingDataCount() > 0
    }
}

