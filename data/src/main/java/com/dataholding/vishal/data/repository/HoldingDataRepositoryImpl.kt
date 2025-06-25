package com.dataholding.vishal.data.repository

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.data.mapper.HoldingDataMapper
import com.dataholding.vishal.data.remote.api.HoldingDataApiService
import com.dataholding.vishal.data.remote.handler.NetworkHandler
import com.dataholding.vishal.domain.model.HoldingData
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import com.dataholding.vishal.domain.usecase.HoldingDataBusinessLogic
import javax.inject.Inject

/**
 * Implementation of [HoldingDataRepository] for accessing holding data from remote sources.
 *
 * Usage:
 * Used in the data layer to fetch holding data from the API and map it to domain models.
 *
 * @property holdingDataApiService The API service for network requests.
 * @property holdingDataMapper The mapper for converting API responses to domain models.
 * @property holdingDataBusinessLogic The business logic class for calculations (if needed).
 * @property networkHandler The network handler for safe API calls with retry mechanism.
 * @constructor Injects dependencies for repository implementation.
 */
class HoldingDataRepositoryImpl @Inject constructor(
    private val holdingDataApiService: HoldingDataApiService,
    private val holdingDataMapper: HoldingDataMapper,
    private val holdingDataBusinessLogic: HoldingDataBusinessLogic,
    private val networkHandler: NetworkHandler
) : HoldingDataRepository {
    /**
     * Fetches holding data from the API and maps it to a list of [HoldingData] domain models.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     *
     * Usage:
     * Call this function from a use case to retrieve holding data for business logic processing.
     */
    override suspend fun apiCallForGetHoldingData(): Either<Failure, List<HoldingData?>?> {
        return networkHandler.safeApiCall(
            apiCall = { holdingDataApiService.apiCallForGetHoldingData() },
            mapper = { holdingDataMapper.map(it) }
        )
    }
}

