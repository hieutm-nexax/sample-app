package com.nexax.sample_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.nexax.sdk.api.AdSDK
import com.nexax.sdk.api.activity.NexaxSplashActivity
import com.nexax.sdk.api.model.AdUnitIds
import com.nexax.sdk.api.model.AppConfig
import com.nexax.sdk.api.model.BrandingConfig
import com.nexax.sdk.api.model.InAppAd
import com.nexax.sdk.api.model.LaunchSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : NexaxSplashActivity() {
    private var tapCount = 0
    private val resetHandler = Handler(Looper.getMainLooper())
    private val resetRunnable = Runnable { tapCount = 0 }

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }
    
    @SuppressLint("AdvertisingIdPolicy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Setup Easter Egg on app logo (only in debug build)
        if (BuildConfig.DEBUG) {
            setupEasterEgg()
        }

        Thread {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this)
                val advertisingId = adInfo.id
                val isLimitAdTrackingEnabled = adInfo.isLimitAdTrackingEnabled

                Log.d("GAID", advertisingId ?: "FAILED to get")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
    
    private fun setupEasterEgg() {
        val logoView = findViewById<ImageView>(R.id.iv_app_logo)

        if (logoView == null) {
            Log.e("SPLASH_TEST", "Logo view not found!")
            return
        }

        Log.e("SPLASH_TEST", "Logo view found - setting up tap listener")

        logoView.setOnClickListener {
            tapCount++
            
            // Reset tap count after 2 seconds of inactivity
            resetHandler.removeCallbacks(resetRunnable)
            resetHandler.postDelayed(resetRunnable, 2000)

            when (tapCount) {
                5 -> {
                    Log.e("SPLASH_TEST", "5 taps - enabling test ad mode")
                    // Enable test ad mode
                    if (!AdSDK.isDebugModeEnabled()) {
                        AdSDK.enableDebugMode()
                        Toast.makeText(this, "✅ Tester Ad Mode Enabled", Toast.LENGTH_LONG).show()
                    }
                }
                10 -> {
                    Log.e("SPLASH_TEST", "10 taps - opening Ad Inspector")
                    // Open Ad Inspector
                    Toast.makeText(this, "🔍 Opening Ad Inspector...", Toast.LENGTH_SHORT).show()
                    AdSDK.openAdInspector(this)
                    tapCount = 0
                }
            }
        }
    }
    
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun adConfig(): AdUnitIds {
        return AdUnitIds(
            interSplash2ID = BuildConfig.interSplash2ID,
            interSplash = BuildConfig.interSplash,
            nativeSplash2ID = BuildConfig.nativeSplash2ID,
            bannerSplash2ID = BuildConfig.bannerSplash,
            bannerSplash = BuildConfig.bannerSplash,

            lfo1Native2ID = BuildConfig.lfo1Native2ID,
            lfo1Native = BuildConfig.lfo1Native,
            lfo2Native2ID = BuildConfig.lfo2Native2ID,
            lfo2Native = BuildConfig.lfo2Native,

            ob1Native2ID = BuildConfig.ob1Native2ID,
            ob1Native = BuildConfig.ob1Native,
            obFull1Native2ID = BuildConfig.obFull1Native2ID,
            obFull1Native = BuildConfig.obFull1Native,
            ob3Native2ID = BuildConfig.ob3Native,
            ob3Native = BuildConfig.ob3Native,

            fuAf02Na2ID = BuildConfig.fuAf02Na2ID,
            fuAf02Na = BuildConfig.fuAf02Na,
            fuAf033Na2ID = BuildConfig.fuAf033Na2ID,
            fuAf033Na = BuildConfig.fuAf033Na,

            openResume2ID = BuildConfig.openResume2ID,
            openResume = BuildConfig.openResume,
            interResume2ID = BuildConfig.interResume2ID
        )
    }

    override fun setAppConfig(): AppConfig {
        return AppConfig(
            showNavigationBar = false,
            showSystemBar = false,
            themeMode = AppConfig.ThemeMode.LIGHT_MODE
        )
    }

    override fun setBrandingConfig(): BrandingConfig {
        return BrandingConfig(
            logoRes = R.drawable.logo_sample,
            appName = R.string.app_name
        )
    }

    override fun toLanguageScreen() {
        val intent = Intent(this, LFOActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun toOnBoardingScreen() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun toHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getPremiumState(onResult: (Boolean) -> Unit) {
        lifecycleScope.launch {
            delay(3000)
            onResult(false)
            Log.d("TAG___STATE_PRM", "getPremiumState: ${AdSDK.isNotShowAds}")
        }
    }

    override fun inAppAdConfig(): List<InAppAd> {
        return listOf(
            InAppAd(
                adName = "home_test",
                native2Id = BuildConfig.nativeHomeTest2ID,
                nativeId = BuildConfig.nativeHomeTest,
                inter2Id = BuildConfig.interHomeTest2ID,
                interId = BuildConfig.interHomeTest
            )
        )
    }

    override fun getAppLaunchSource(): LaunchSource {
        return LaunchSource.NORMAL
    }
}