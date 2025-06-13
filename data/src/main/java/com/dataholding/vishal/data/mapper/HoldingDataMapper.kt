package com.dataholding.vishal.data.mapper

import com.dataholding.vishal.core.mapper.ResultMapper
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.domain.model.HoldingData
import javax.inject.Inject

class HoldingDataMapper @Inject constructor() :
    ResultMapper<HoldingDataApiResponseModel, List<HoldingData?>?> {
    override fun map(map: HoldingDataApiResponseModel): List<HoldingData?>? {
        return map.responseData?.userHolding?.map { userHolding ->
            HoldingData(
                symbol = userHolding?.symbol,
                avgPrice = userHolding?.avgPrice,
                close = userHolding?.close,
                ltp = userHolding?.ltp,
                quantity = userHolding?.quantity
            )
        }
    }
}