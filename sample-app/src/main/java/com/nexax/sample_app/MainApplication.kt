package com.nexax.sample_app

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.google.firebase.FirebaseApp
import com.nexax.sdk.api.AdSDK
import com.nexax.sdk.api.app.NexaxBaseApplication

class MainApplication: NexaxBaseApplication() {
    // License key generated for com.nexax.sample_app package (exact from logs)
    private val LICENSE_KEY = "WhPU58TdanjqyVUJcrAFr3Vdhd13RioMQWc/bXaJw5U6i6/M5om8GLAa7hi4nFZeruJRSKYEYZ/3et9136L3uA=="

    override fun splashActivity(): Class<out Activity> {
        return SplashActivity::class.java
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        registerActivityLifecycleCallbacks(this)

        // Init Adjust
        AdSDK.initAdjust(
            application = this,
            appToken = "om613j4tpl34",
            environment = AdjustConfig.ENVIRONMENT_SANDBOX,
            logLevel = LogLevel.VERBOSE,
            fbAppId = "1224642173014632"
        )
        
        // Initialize Nexax SDK with license validation
        try {
            Log.d("MainApplication", "Initializing Nexax SDK with license...")
            AdSDK.initialize(this, BuildConfig.DEBUG, LICENSE_KEY)
            Log.d("MainApplication", "Nexax SDK initialized successfully")
        } catch (e: SecurityException) {
            Log.e("MainApplication", "License validation failed: ${e.message}")
            // In production, you might want to disable ads functionality or show error message
        } catch (e: Exception) {
            Log.e("MainApplication", "Failed to initialize Nexax SDK", e)
        }

        // Initialize event token
        AdSDK.setEventToken(
            purchase = "e69zmi"
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d("APPLICATION_LIFECYCLE", "attachBaseContext: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("APPLICATION_LIFECYCLE", "onConfigurationChanged: ")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("APPLICATION_LIFECYCLE", "onTerminate: ")
    }
}