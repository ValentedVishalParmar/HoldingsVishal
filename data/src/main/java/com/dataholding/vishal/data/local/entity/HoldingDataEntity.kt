package com.dataholding.vishal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing holding data locally.
 *
 * Usage:
 * Used to cache holding data in the local database for offline access.
 * Provides fallback support when network is unavailable.
 *
 * @property id Primary key for the entity.
 * @property symbol Stock symbol.
 * @property quantity Number of shares held.
 * @property ltp Last traded price.
 * @property avgPrice Average purchase price.
 * @property close Closing price.
 * @property todayInvestment Today's investment value.
 * @property currentValue Current market value.
 * @property totalProfitAndLoss Total profit/loss.
 * @property totalInvestmentValue Total investment value.
 * @property todayProfitLoss Today's profit/loss.
 * @property lastUpdated Timestamp when data was last updated.
 */
@Entity(tableName = "holding_data")
data class HoldingDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symbol: String?,
    val quantity: Int?,
    val ltp: Double?,
    val avgPrice: Double?,
    val close: Double?,
    val todayInvestment: Double,
    val currentValue: Double,
    val totalProfitAndLoss: Double,
    val totalInvestmentValue: Double,
    val todayProfitLoss: Double,
    val lastUpdated: Long = System.currentTimeMillis()
) 