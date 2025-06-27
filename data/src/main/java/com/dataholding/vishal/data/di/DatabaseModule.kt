package com.dataholding.vishal.data.di

import android.content.Context
import com.dataholding.vishal.data.local.dao.HoldingDataDao
import com.dataholding.vishal.data.local.database.AppDatabase
import com.dataholding.vishal.data.local.datasource.HoldingDataLocalDataSource
import com.dataholding.vishal.data.local.datasource.HoldingDataLocalDataSourceImpl
import com.dataholding.vishal.core.util.KeystoreUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.sqlcipher.database.SupportFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Hilt module for database dependencies.
 *
 * Usage:
 * Provides Room database, DAO, and local data source dependencies.
 * Supports dependency injection for offline-first functionality.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides Room database instance.
     *
     * @param context Application context.
     * @return AppDatabase instance.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase = KeystoreUtils.getOrCreatePassphrase(context)
        return AppDatabase.getDatabase(context, passphrase)
    }

    /**
     * Provides HoldingDataDao from the database.
     *
     * @param database AppDatabase instance.
     * @return HoldingDataDao instance.
     */
    @Provides
    @Singleton
    fun provideHoldingDataDao(database: AppDatabase): HoldingDataDao {
        return database.holdingDataDao()
    }

    /**
     * Provides HoldingDataLocalDataSource implementation.
     *
     * @param holdingDataDao Data Access Object for holding data.
     * @return HoldingDataLocalDataSource implementation.
     */
    @Provides
    @Singleton
    fun provideHoldingDataLocalDataSource(
        holdingDataDao: HoldingDataDao
    ): HoldingDataLocalDataSource {
        return HoldingDataLocalDataSourceImpl(holdingDataDao)
    }
} 