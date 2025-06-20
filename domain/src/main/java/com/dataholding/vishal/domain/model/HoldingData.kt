package com.dataholding.vishal.domain.model

data class HoldingData(
    val symbol: String?,
    val quantity: Int?,
    val ltp: Double?,
    val avgPrice: Double?,
    val close: Double?,
    var todayInvestment: Double = 0.0,
    var currentValue: Double = 0.0,
    var totalProfitAndLoss: Double = 0.0,
    var totalInvestmentValue: Double = 0.0,
    var todayProfitLoss: Double = 0.0,
    )
