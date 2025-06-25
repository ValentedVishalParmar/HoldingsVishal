package com.dataholding.vishal.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.dataholding.vishal.data.local.dao.HoldingDataDao
import com.dataholding.vishal.data.local.entity.HoldingDataEntity

/**
 * Room database for the application.
 *
 * Usage:
 * Provides local database functionality for caching data offline.
 * Supports fallback scenarios when network is unavailable.
 *
 * @property holdingDataDao Data Access Object for holding data operations.
 */
@Database(
    entities = [HoldingDataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Data Access Object for holding data operations.
     */
    abstract fun holdingDataDao(): HoldingDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the database instance (singleton pattern).
         *
         * @param context Application context.
         * @return Database instance.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 