package com.dataholding.vishal.data.local.datasource

import com.dataholding.vishal.domain.model.HoldingData
import kotlinx.coroutines.flow.Flow

/**
 * Local data source interface for holding data operations.
 *
 * Usage:
 * Defines contract for local database operations.
 * Supports offline-first functionality with caching.
 */
interface HoldingDataLocalDataSource {

    /**
     * Saves holding data to local database.
     *
     * @param holdingDataList List of holding data to save.
     */
    suspend fun saveHoldingData(holdingDataList: List<HoldingData>)

    /**
     * Retrieves all holding data from local database as a Flow.
     *
     * @return Flow of holding data list.
     */
    fun getHoldingData(): Flow<List<HoldingData>>

    /**
     * Retrieves all holding data from local database as a list.
     *
     * @return List of holding data.
     */
    suspend fun getHoldingDataList(): List<HoldingData>

    /**
     * Retrieves holding data by symbol.
     *
     * @param symbol Stock symbol.
     * @return Flow of holding data for the symbol.
     */
    fun getHoldingDataBySymbol(symbol: String): Flow<HoldingData?>

    /**
     * Clears all holding data from local database.
     */
    suspend fun clearHoldingData()

    /**
     * Gets the count of holding data records.
     *
     * @return Number of records.
     */
    suspend fun getHoldingDataCount(): Int

    /**
     * Gets the timestamp of the most recently updated record.
     *
     * @return Timestamp of last update, or null if no data exists.
     */
    suspend fun getLastUpdatedTimestamp(): Long?

    /**
     * Checks if data is stale (older than specified time).
     *
     * @param maxAgeMillis Maximum age in milliseconds.
     * @return True if data is stale or doesn't exist.
     */
    suspend fun isDataStale(maxAgeMillis: Long): Boolean
} 