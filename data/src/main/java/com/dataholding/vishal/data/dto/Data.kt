package com.dataholding.vishal.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("userHolding")
    val userHolding: List<UserHolding?>?
)