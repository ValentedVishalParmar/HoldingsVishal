package com.dataholding.vishal.data.di

import com.dataholding.vishal.data.remote.api.HoldingDataApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val networkJson = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideBaseUrl() = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io"


    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideRetrofit(url: String, loggingInterceptor: HttpLoggingInterceptor): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HoldingDataApiService =
        retrofit.create(HoldingDataApiService::class.java)
}

