package com.dataholding.vishal.domain.usecase

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.core.functional.fold
import com.dataholding.vishal.domain.model.HoldingData
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import javax.inject.Inject

/**
 * Use case for retrieving and processing holding data.
 *
 * Usage:
 * Called from the presentation layer (ViewModel) to fetch holding data, apply business logic,
 * and return the result to the UI layer.
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
     * Retrieves holding data from the repository, applies business logic, and returns the result.
     *
     * @return [Either] containing [Failure] on error or a list of [HoldingData] on success.
     *
     * Usage:
     * Call this function from the ViewModel to get processed holding data for the UI.
     */
    suspend fun getInvokeHoldingDataApiCall(): Either<Failure, List<HoldingData?>?> {
        val data = holdingDataRepository.apiCallForGetHoldingData()
        return data.fold(
            { Either.Left(it) },
            { mappedList -> Either.Right(businessLogic.applyBusinessLogic(mappedList)) }
        )
    }
    

}