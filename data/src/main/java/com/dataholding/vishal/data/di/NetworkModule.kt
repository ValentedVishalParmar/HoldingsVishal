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
import okhttp3.CertificatePinner

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
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        // TODO: Replace with your real domain and SHA-256 hash
        val certificatePinner = CertificatePinner.Builder()
            .add("35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io", "sha256/aNTI4TpC4IPJTWCAxWWZXs9KZmIGBn8Pv/JTbBD5yWY=").build()
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(url: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HoldingDataApiService =
        retrofit.create(HoldingDataApiService::class.java)
}

