package com.tan.master_detail42_ac

import android.app.Application
import com.tan.master_detail42_ac.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp

/**
 * A class that inherits from Application, with annotation informing that we will use Hilt in the app.
 */
@HiltAndroidApp
class MainApplication : Application()  {
    override fun onCreate() {
        super.onCreate()

        // Initialize AppPreferences
        AppPreferences.init(this)
    }
}