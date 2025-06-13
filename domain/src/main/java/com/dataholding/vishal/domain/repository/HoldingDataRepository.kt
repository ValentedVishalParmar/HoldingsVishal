package com.dataholding.vishal.domain.repository

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.domain.model.HoldingData

interface HoldingDataRepository {

    suspend fun getHoldingData() : Either<Failure, List<HoldingData?>?>
}