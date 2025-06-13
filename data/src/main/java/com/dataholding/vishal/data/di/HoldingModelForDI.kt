package com.dataholding.vishal.data.di

import com.dataholding.vishal.data.repository.HoldingDataRepositoryImpl
import com.dataholding.vishal.domain.repository.HoldingDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//todo:: 16] define this class to bind the repository to its implementation
@Module
@InstallIn(SingletonComponent::class)
abstract class HoldingModelForDI {

    @Binds
    abstract fun bindHoldingDataRepository(holdingDataRepositoryImpl: HoldingDataRepositoryImpl): HoldingDataRepository
}