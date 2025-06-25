package com.dataholding.vishal.domain.usecase

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.core.functional.fold
import com.dataholding.vishal.domain.model.HoldingData
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for retrieving and processing holding data with offline-first support.
 *
 * Usage:
 * Called from the presentation layer (ViewModel) to fetch holding data, apply business logic,
 * and return the result to the UI layer. Supports offline-first functionality.
 *
 * @property holdingDataRepository The repository interface for accessing holding data.
 * @property businessLogic The business logic class for applying calculations to the data.
 * @constructor Injects dependencies for use case execution.
 */
class HoldingDataUseCase @Inject constructor(
    private val holdingDataRepository: HoldingDataRepository,
    private val businessLogic: HoldingDataBusinessLogic
) {
    
    /**
     * Retrieves holding data from the repository with offline-first approach, applies business logic, and returns the result.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     *
     * Usage:
     * Call this function from the ViewModel to get processed holding data for the UI.
     * Supports fallback to cached data when network is unavailable.
     */
    suspend fun getInvokeHoldingDataApiCall(): Either<Failure, List<HoldingData?>?> {
        val data = holdingDataRepository.apiCallForGetHoldingData()
        return data.fold(
            { Either.Left(it) },
            { mappedList ->
                val processed = businessLogic.applyBusinessLogic(mappedList)
                // Save processed data to DB
                holdingDataRepository.saveProcessedHoldingsToDb(processed?.filterNotNull() ?: emptyList())
                Either.Right(processed)
            }
        )
    }

    /**
     * Gets holding data as a Flow for reactive updates.
     * Provides real-time updates from both API and local cache.
     *
     * @return Flow of holding data list with business logic applied.
     */
    fun getHoldingDataFlow(): Flow<List<HoldingData?>?> {
        return holdingDataRepository.getHoldingDataFlow().map { cachedData ->
            businessLogic.applyBusinessLogic(cachedData) ?: emptyList()
        }
    }

    /**
     * Gets holding data by symbol from local cache.
     *
     * @param symbol Stock symbol.
     * @return Flow of holding data for the symbol.
     */
    fun getHoldingDataBySymbol(symbol: String): Flow<HoldingData?> {
        return holdingDataRepository.getHoldingDataBySymbol(symbol)
    }

    /**
     * Refreshes data from API and updates local cache.
     * Ignores data freshness check and forces a refresh.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    suspend fun refreshHoldingData(): Either<Failure, List<HoldingData?>?> {
        val data = holdingDataRepository.refreshHoldingData()
        return data.fold(
            { Either.Left(it) },
            { mappedList -> Either.Right(businessLogic.applyBusinessLogic(mappedList)) }
        )
    }

    /**
     * Gets holding data with smart caching.
     * Uses cached data if it's fresh enough, otherwise fetches from API.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     */
    suspend fun getHoldingDataWithSmartCache(): Either<Failure, List<HoldingData?>?> {
        val data = holdingDataRepository.getHoldingDataWithSmartCache()
        return data.fold(
            { Either.Left(it) },
            { mappedList -> Either.Right(businessLogic.applyBusinessLogic(mappedList)) }
        )
    }

    /**
     * Clears all cached holding data.
     */
    suspend fun clearCachedData() {
        holdingDataRepository.clearCachedData()
    }

    /**
     * Gets the count of cached holding data records.
     *
     * @return Number of cached records.
     */
    suspend fun getCachedDataCount(): Int {
        return holdingDataRepository.getCachedDataCount()
    }

    /**
     * Checks if cached data exists and is fresh.
     *
     * @return True if fresh cached data is available.
     */
    suspend fun hasFreshCachedData(): Boolean {
        return holdingDataRepository.hasFreshCachedData()
    }
}