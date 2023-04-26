package com.sky.skygomovie.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SkyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}