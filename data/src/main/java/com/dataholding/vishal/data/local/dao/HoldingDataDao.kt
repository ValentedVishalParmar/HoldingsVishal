package com.dataholding.vishal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dataholding.vishal.data.local.entity.HoldingDataEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for holding data database operations.
 *
 * Usage:
 * Provides methods to interact with the local holding data database.
 * Supports offline-first functionality with caching and retrieval.
 */
@Dao
interface HoldingDataDao {

    /**
     * Inserts or updates holding data entities.
     * Uses REPLACE strategy to handle conflicts.
     *
     * @param entities List of holding data entities to insert/update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldingData(entities: List<HoldingDataEntity>)

    /**
     * Retrieves all holding data as a Flow for reactive updates.
     *
     * @return Flow of list of holding data entities.
     */
    @Query("SELECT * FROM holding_data ORDER BY symbol ASC")
    fun getAllHoldingData(): Flow<List<HoldingDataEntity>>

    /**
     * Retrieves all holding data as a list (for one-time queries).
     *
     * @return List of holding data entities.
     */
    @Query("SELECT * FROM holding_data ORDER BY symbol ASC")
    suspend fun getAllHoldingDataList(): List<HoldingDataEntity>

    /**
     * Retrieves holding data by symbol.
     *
     * @param symbol The stock symbol to search for.
     * @return Flow of holding data entity for the given symbol.
     */
    @Query("SELECT * FROM holding_data WHERE symbol = :symbol")
    fun getHoldingDataBySymbol(symbol: String): Flow<HoldingDataEntity?>

    /**
     * Deletes all holding data from the database.
     */
    @Query("DELETE FROM holding_data")
    suspend fun deleteAllHoldingData()

    /**
     * Deletes holding data older than the specified timestamp.
     *
     * @param timestamp The timestamp threshold for deletion.
     */
    @Query("DELETE FROM holding_data WHERE lastUpdated < :timestamp")
    suspend fun deleteOldHoldingData(timestamp: Long)

    /**
     * Gets the count of holding data records.
     *
     * @return Number of records in the database.
     */
    @Query("SELECT COUNT(*) FROM holding_data")
    suspend fun getHoldingDataCount(): Int

    /**
     * Gets the timestamp of the most recently updated record.
     *
     * @return Timestamp of the most recent update, or null if no data exists.
     */
    @Query("SELECT MAX(lastUpdated) FROM holding_data")
    suspend fun getLastUpdatedTimestamp(): Long?
} 