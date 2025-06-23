package com.dataholding.vishal.data.mapper

import android.util.Log
import com.dataholding.vishal.core.mapper.ResultMapper
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.domain.model.HoldingData
import javax.inject.Inject
import kotlin.Double

class HoldingDataMapper @Inject constructor() :
    ResultMapper<HoldingDataApiResponseModel, List<HoldingData?>?> {
    override fun map(map: HoldingDataApiResponseModel): List<HoldingData?>? {
            var totalProfitAndLoss: Double = 0.0
            var totalInvestmentValue: Double = 0.0
        Log.e("userHolding>>>", "map: ${map.responseData?.userHolding}", )
        return map.responseData?.userHolding?.map { userHolding ->
            var todayInvestment: Double = 0.0
            var currentValue: Double = 0.0
            var todayProfitLoss: Double = 0.0

            userHolding?.let { newData ->
                with(newData) {
                    ltp?.let { it ->
                        quantity?.let { it1 ->
                            close?.let { it2 ->
                                todayProfitLoss += (it2 - it) * it1
                            }
                            currentValue = currentValue.plus(it1 * it)
                        }
                    }

                    avgPrice?.let { it ->
                        quantity?.let { it1 ->
                            todayInvestment = it * it1
                            totalInvestmentValue = totalInvestmentValue.plus(it1 * it)
                        }
                    }

                    totalProfitAndLoss = totalProfitAndLoss.plus(
                        currentValue.minus(totalInvestmentValue)
                    )

                }

                HoldingData(
                    symbol = userHolding.symbol,
                    avgPrice = userHolding.avgPrice,
                    close = userHolding.close,
                    ltp = userHolding.ltp,
                    quantity = userHolding.quantity,
                    todayInvestment = todayInvestment,
                currentValue =  currentValue,
                totalProfitAndLoss =  totalProfitAndLoss,
                totalInvestmentValue =  totalInvestmentValue,
                    todayProfitLoss =  todayProfitLoss,
                )
            }
        }
    }
}