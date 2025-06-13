package com.dataholding.vishal.domain.usecase

import com.dataholding.vishal.domain.repository.HoldingDataRepository
import javax.inject.Inject

//todo:: 17] define the use case file for call the repository function we need repository to be inject here.
class HoldingDataUseCase @Inject constructor(private val holdingDataRepository: HoldingDataRepository) {
    suspend fun getInvokeHoldingDataApiCall() = holdingDataRepository.getHoldingData()

}