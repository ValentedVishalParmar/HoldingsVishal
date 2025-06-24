package com.dataholding.vishal.domain.usecase

import com.dataholding.vishal.domain.model.HoldingData
import javax.inject.Inject

/**
 * Business logic class for holding data calculations.
 *
 * Usage:
 * Used in the domain layer to apply profit/loss and investment calculations to a list of [HoldingData] domain models.
 *
 * @constructor Injects the business logic class for dependency injection frameworks (e.g., Hilt).
 */
class HoldingDataBusinessLogic @Inject constructor() {
    
    /**
     * Applies business logic for calculating profit/loss and investment values to a list of [HoldingData].
     *
     * @param holdings List of [HoldingData] domain models to process. Can be null or contain nulls.
     * @return List of [HoldingData] with calculated fields, or null if input is null or empty.
     *
     * Usage:
     * Call this function from a use case to process mapped holding data before returning to the UI.
     */
    fun applyBusinessLogic(holdings: List<HoldingData?>?): List<HoldingData?>? {
        if (holdings.isNullOrEmpty()) return null
        var totalProfitAndLoss = 0.0
        var totalInvestmentValue = 0.0

        val processed = holdings.map { holding ->
            holding?.let {
                val todayInvestment = (it.avgPrice ?: 0.0) * (it.quantity ?: 0)
                val currentValue = (it.ltp ?: 0.0) * (it.quantity ?: 0)
                val todayProfitLoss = ((it.close ?: 0.0) - (it.ltp ?: 0.0)) * (it.quantity ?: 0)
                totalInvestmentValue += todayInvestment
                totalProfitAndLoss += (currentValue - todayInvestment)
                it.copy(
                    todayInvestment = todayInvestment,
                    currentValue = currentValue,
                    todayProfitLoss = todayProfitLoss,
                    totalInvestmentValue = totalInvestmentValue,
                    totalProfitAndLoss = 0.0 // will set below
                )
            }
        }
        // Set totalProfitAndLoss for all
        return processed.map { it?.copy(totalProfitAndLoss = totalProfitAndLoss) }
    }
} 