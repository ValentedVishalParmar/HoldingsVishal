package com.example.holdingsvishal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HoldingsVishalApp : Application() {

    companion object;

    override fun onCreate() {
        super.onCreate()

        init()
    }


    private fun init() {

    }

}