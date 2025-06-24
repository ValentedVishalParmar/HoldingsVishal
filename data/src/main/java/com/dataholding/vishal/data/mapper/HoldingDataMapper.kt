package com.dataholding.vishal.data.mapper

import com.dataholding.vishal.core.mapper.ResultMapper
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.domain.model.HoldingData
import javax.inject.Inject

/**
 * Mapper class to convert [HoldingDataApiResponseModel] DTOs to a list of [HoldingData] domain models.
 *
 * Usage:
 * Used in the data layer to map API response models to domain models for further processing in the domain layer.
 *
 * @constructor Injects the mapper for dependency injection frameworks (e.g., Hilt).
 */
class HoldingDataMapper @Inject constructor() :
    ResultMapper<HoldingDataApiResponseModel, List<HoldingData?>?> {

    /**
     * Maps a [HoldingDataApiResponseModel] to a list of [HoldingData] domain models.
     *
     * @param map The API response model to be mapped.
     * @return List of [HoldingData] domain models, or null if the response data is null.
     */
    override fun map(map: HoldingDataApiResponseModel): List<HoldingData?>? {
        return map.responseData?.userHolding?.map { userHolding ->
            userHolding?.let {
                HoldingData(
                    symbol = it.symbol,
                    avgPrice = it.avgPrice,
                    close = it.close,
                    ltp = it.ltp,
                    quantity = it.quantity,
                    todayInvestment = 0.0,
                    currentValue = 0.0,
                    totalProfitAndLoss = 0.0,
                    totalInvestmentValue = 0.0,
                    todayProfitLoss = 0.0,
                )
            }
        }
    }
}