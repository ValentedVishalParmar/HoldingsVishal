package com.dataholding.vishal.data.remote.api

import com.dataholding.vishal.data.dto.Data
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface HoldingDataApiService {

    @GET("/")
    suspend fun apiCallForGetHoldingData(): Response<HoldingDataApiResponseModel>

}