package com.dataholding.vishal.data.local.datasource

import com.dataholding.vishal.data.local.dao.HoldingDataDao
import com.dataholding.vishal.data.local.mapper.HoldingDataMapper
import com.dataholding.vishal.domain.model.HoldingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of HoldingDataLocalDataSource.
 *
 * Usage:
 * Provides local database operations for holding data caching.
 * Supports offline-first functionality with Room database.
 *
 * @property holdingDataDao Data Access Object for database operations.
 * @constructor Injects the DAO for dependency injection.
 */
@Singleton
class HoldingDataLocalDataSourceImpl @Inject constructor(
    private val holdingDataDao: HoldingDataDao
) : HoldingDataLocalDataSource {

    /**
     * Saves holding data to local database.
     *
     * @param holdingDataList List of holding data to save.
     */
    override suspend fun saveHoldingData(holdingDataList: List<HoldingData>) {
        val entities = HoldingDataMapper.mapToEntityList(holdingDataList)
        holdingDataDao.insertHoldingData(entities)
    }

    /**
     * Retrieves all holding data from local database as a Flow.
     *
     * @return Flow of holding data list.
     */
    override fun getHoldingData(): Flow<List<HoldingData>> {
        return holdingDataDao.getAllHoldingData().map { entities ->
            HoldingDataMapper.mapToDomainList(entities)
        }
    }

    /**
     * Retrieves all holding data from local database as a list.
     *
     * @return List of holding data.
     */
    override suspend fun getHoldingDataList(): List<HoldingData> {
        val entities = holdingDataDao.getAllHoldingDataList()
        return HoldingDataMapper.mapToDomainList(entities)
    }

    /**
     * Retrieves holding data by symbol.
     *
     * @param symbol Stock symbol.
     * @return Flow of holding data for the symbol.
     */
    override fun getHoldingDataBySymbol(symbol: String): Flow<HoldingData?> {
        return holdingDataDao.getHoldingDataBySymbol(symbol).map { entity ->
            entity?.let { HoldingDataMapper.mapToDomain(it) }
        }
    }

    /**
     * Clears all holding data from local database.
     */
    override suspend fun clearHoldingData() {
        holdingDataDao.deleteAllHoldingData()
    }

    /**
     * Gets the count of holding data records.
     *
     * @return Number of records.
     */
    override suspend fun getHoldingDataCount(): Int {
        return holdingDataDao.getHoldingDataCount()
    }

    /**
     * Gets the timestamp of the most recently updated record.
     *
     * @return Timestamp of last update, or null if no data exists.
     */
    override suspend fun getLastUpdatedTimestamp(): Long? {
        return holdingDataDao.getLastUpdatedTimestamp()
    }

    /**
     * Checks if data is stale (older than specified time).
     *
     * @param maxAgeMillis Maximum age in milliseconds.
     * @return True if data is stale or doesn't exist.
     */
    override suspend fun isDataStale(maxAgeMillis: Long): Boolean {
        val lastUpdated = getLastUpdatedTimestamp()
        return if (lastUpdated != null) {
            val currentTime = System.currentTimeMillis()
            (currentTime - lastUpdated) > maxAgeMillis
        } else {
            true // No data exists, consider it stale
        }
    }
} 