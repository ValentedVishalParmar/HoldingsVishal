package com.dataholding.vishal.data.local.mapper

import com.dataholding.vishal.data.local.entity.HoldingDataEntity
import com.dataholding.vishal.domain.model.HoldingData

/**
 * Mapper for converting between HoldingDataEntity and HoldingData domain model.
 *
 * Usage:
 * Used to convert between local database entities and domain models.
 * Supports bidirectional mapping for caching and retrieval.
 */
object HoldingDataMapper {

    /**
     * Converts a list of HoldingDataEntity to a list of HoldingData domain models.
     *
     * @param entities List of database entities.
     * @return List of domain models.
     */
    fun mapToDomainList(entities: List<HoldingDataEntity>): List<HoldingData> {
        return entities.map { mapToDomain(it) }
    }

    /**
     * Converts a HoldingDataEntity to a HoldingData domain model.
     *
     * @param entity Database entity.
     * @return Domain model.
     */
    fun mapToDomain(entity: HoldingDataEntity): HoldingData {
        return HoldingData(
            symbol = entity.symbol ?: "",
            quantity = entity.quantity ?: 0,
            ltp = entity.ltp ?: 0.0,
            avgPrice = entity.avgPrice ?: 0.0,
            close = entity.close ?: 0.0,
            todayInvestment = entity.todayInvestment,
            currentValue = entity.currentValue,
            totalProfitAndLoss = entity.totalProfitAndLoss,
            totalInvestmentValue = entity.totalInvestmentValue,
            todayProfitLoss = entity.todayProfitLoss
        )
    }

    /**
     * Converts a list of HoldingData domain models to a list of HoldingDataEntity.
     *
     * @param domainModels List of domain models.
     * @return List of database entities.
     */
    fun mapToEntityList(domainModels: List<HoldingData>): List<HoldingDataEntity> {
        return domainModels.map { mapToEntity(it) }
    }

    /**
     * Converts a HoldingData domain model to a HoldingDataEntity.
     *
     * @param domainModel Domain model.
     * @return Database entity.
     */
    fun mapToEntity(domainModel: HoldingData): HoldingDataEntity {
        return HoldingDataEntity(
            symbol = domainModel.symbol,
            quantity = domainModel.quantity,
            ltp = domainModel.ltp,
            avgPrice = domainModel.avgPrice,
            close = domainModel.close,
            todayInvestment = domainModel.todayInvestment,
            currentValue = domainModel.currentValue,
            totalProfitAndLoss = domainModel.totalProfitAndLoss,
            totalInvestmentValue = domainModel.totalInvestmentValue,
            todayProfitLoss = domainModel.todayProfitLoss,
            lastUpdated = System.currentTimeMillis()
        )
    }
} 