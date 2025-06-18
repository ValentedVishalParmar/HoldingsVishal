package com.dataholding.vishal.data.repository

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.data.dto.Data
import com.dataholding.vishal.data.dto.HoldingDataApiResponseModel
import com.dataholding.vishal.data.dto.UserHolding
import com.dataholding.vishal.data.mapper.HoldingDataMapper
import com.dataholding.vishal.data.remote.api.HoldingDataApiService
import com.dataholding.vishal.data.remote.handler.safeApiCall
import com.dataholding.vishal.domain.model.HoldingData
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

class HoldingDataRepositoryImpl @Inject constructor(
    private val holdingDataApiService: HoldingDataApiService,
    private val holdingDataMapper: HoldingDataMapper) : HoldingDataRepository {
    override suspend fun apiCallForGetHoldingData(): Either<Failure, List<HoldingData?>?> {
       val data = safeApiCall(
           apiCall = { holdingDataApiService.apiCallForGetHoldingData()},
           mapper = { holdingDataMapper.map(it)  }
       )

        return data
    }

}

