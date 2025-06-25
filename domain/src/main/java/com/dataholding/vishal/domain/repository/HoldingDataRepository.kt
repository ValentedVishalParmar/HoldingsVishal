package com.dataholding.vishal.domain.repository

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.domain.model.HoldingData
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for holding data operations with offline-first support.
 *
 * Usage:
 * Defines contract for holding data operations including API calls and local caching.
 * Supports fallback scenarios when network is unavailable.
 */
interface HoldingDataRepository {

    /**
     * Fetches holding data with offline-first approach.
     * First tries to get fresh data from API, falls back to local cache if network fails.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    suspend fun apiCallForGetHoldingData(): Either<Failure, List<HoldingData?>?>

    /**
     * Gets holding data as a Flow for reactive updates.
     * Provides real-time updates from both API and local cache.
     *
     * @return Flow of holding data list.
     */
    fun getHoldingDataFlow(): Flow<List<HoldingData?>?>

    /**
     * Gets holding data by symbol from local cache.
     *
     * @param symbol Stock symbol.
     * @return Flow of holding data for the symbol.
     */
    fun getHoldingDataBySymbol(symbol: String): Flow<HoldingData?>

    /**
     * Refreshes data from API and updates local cache.
     * Ignores data freshness check and forces a refresh.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    suspend fun refreshHoldingData(): Either<Failure, List<HoldingData?>?>

    /**
     * Gets holding data with smart caching.
     * Uses cached data if it's fresh enough, otherwise fetches from API.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    suspend fun getHoldingDataWithSmartCache(): Either<Failure, List<HoldingData?>?>

    /**
     * Clears all cached holding data.
     */
    suspend fun clearCachedData()

    /**
     * Gets the count of cached holding data records.
     *
     * @return Number of cached records.
     */
    suspend fun getCachedDataCount(): Int

    /**
     * Checks if cached data exists and is fresh.
     *
     * @return True if fresh cached data is available.
     */
    suspend fun hasFreshCachedData(): Boolean
}