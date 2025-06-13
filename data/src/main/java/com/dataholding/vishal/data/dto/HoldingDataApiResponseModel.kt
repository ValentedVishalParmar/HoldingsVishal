package com.dataholding.vishal.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HoldingDataApiResponseModel(
    @SerialName("data")
    val responseData: Data?
)