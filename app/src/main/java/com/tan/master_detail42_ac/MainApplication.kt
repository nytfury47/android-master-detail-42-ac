package com.tan.master_detail42_ac

import android.app.Application
import com.tan.master_detail42_ac.data.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()  {
    override fun onCreate() {
        super.onCreate()

        // Initialize AppPreferences
        AppPreferences.init(this)
    }
}