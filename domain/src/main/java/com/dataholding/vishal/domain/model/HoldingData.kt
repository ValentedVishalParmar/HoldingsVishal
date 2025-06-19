package com.dataholding.vishal.domain.model

data class HoldingData(
    val symbol: String?,
    val quantity: Int?,
    val ltp: Double?,
    val avgPrice: Double?,
    val close: Double?
)
