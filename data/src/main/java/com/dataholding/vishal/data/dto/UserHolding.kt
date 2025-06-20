package com.dataholding.vishal.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserHolding(
    @SerialName("avgPrice")
    val avgPrice: Double?,
    @SerialName("close")
    val close: Double?,
    @SerialName("ltp")
    val ltp: Double?,
    @SerialName("quantity")
    val quantity: Int?,
    @SerialName("symbol")
    val symbol: String?
)