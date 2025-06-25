package com.dataholding.vishal.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dataholding.vishal.data.local.dao.HoldingDataDao
import com.dataholding.vishal.data.local.entity.HoldingDataEntity
import net.sqlcipher.database.SupportFactory

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
         * Gets the database instance (singleton pattern) with encryption.
         *
         * @param context Application context.
         * @param passphrase Encryption passphrase.
         * @return Database instance.
         */
        fun getDatabase(context: Context, passphrase: String): AppDatabase {
            val passphraseBytes = net.sqlcipher.database.SQLiteDatabase.getBytes(passphrase.toCharArray())
            val factory = SupportFactory(passphraseBytes)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .openHelperFactory(factory) // Enable SQLCipher encryption
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 