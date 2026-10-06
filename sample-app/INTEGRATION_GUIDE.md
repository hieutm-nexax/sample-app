# Partner Integration Guide - Nexax Ads SDK

## Mục đích
Hướng dẫn partner integrate Nexax Ads SDK vào ứng dụng của mình.

## Bước 1: Setup Dependencies

### Add SDK dependency vào build.gradle.kts (app level)
```kotlin
dependencies {
    implementation project(':nexax-ads-sdk')
    // Hoặc nếu sử dụng AAR file
    // implementation files('libs/nexax-ads-sdk.aar')
    
    // Required dependencies
    implementation ("com.google.android.gms:play-services-ads:22.6.0")
    implementation ("com.google.firebase:firebase-core:21.1.1")
}
```

### Add Firebase plugin vào build.gradle.kts (app level)
```kotlin
plugins {
    id("com.google.gms.google-services")
}
```

### Add google-services.json
Copy file `google-services.json` của bạn vào folder `app/`

## Bước 2: Get License Key

### Liên hệ Nexax Team
Email: support@nexax.global với thông tin:
```
Subject: License Request for [Your App Name]
Package Name: com.yourcompany.yourapp
App Description: Brief description
Expected Monthly Users: xxx,xxx
```

### Nhận License Key
Nexax team sẽ gửi license key format:
```
License Key: a1b2c3d4e5f6789012345678901234567890abcdef1234567890abcdef123456
Valid Period: 60 days
Expires: 2026-02-19[sample_app](src/main/java/com/nexax/sample_app)
Package: com.yourcompany.yourapp
```

## Bước 3: Initialize SDK

### Update MainApplication.kt
```kotlin
import com.nexax.sdk.api.AdSDK

class MainApplication: Application() {
    
    // License key được cung cấp bởi Nexax
    private val NEXAX_LICENSE_KEY = "YOUR_LICENSE_KEY_HERE"
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase (if not already done)
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
        
        // Initialize Nexax SDK với license validation
        try {
            Log.d("MyApp", "Initializing Nexax SDK...")
            AdSDK.initialize(this, NEXAX_LICENSE_KEY)
            Log.d("MyApp", "Nexax SDK initialized successfully")
        } catch (e: SecurityException) {
            Log.e("MyApp", "License validation failed: ${e.message}")
            // Handle license failure - disable ads functionality
        } catch (e: Exception) {
            Log.e("MyApp", "Failed to initialize Nexax SDK", e)
        }
    }
}
```

### Register Application trong AndroidManifest.xml
```xml
<application
    android:name=".MainApplication"
    android:theme="@style/AppTheme">
    <!-- Your activities -->
</application>
```

## Bước 4: Load Ads

### Banner Ad Example
```kotlin
import com.nexax.sdk.api.BannerAdManager

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Load banner ad
        val bannerContainer = findViewById<LinearLayout>(R.id.banner_container)
        BannerAdManager.loadBanner(
            activity = this,
            adUnitId = "YOUR_BANNER_AD_UNIT_ID",
            container = bannerContainer
        )
    }
}
```

### Interstitial Ad Example  
```kotlin
import com.nexax.sdk.api.InterstitialAdManager

class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Load và show interstitial
        InterstitialAdManager.loadAndShow(
            activity = this,
            adUnitId = "YOUR_INTERSTITIAL_AD_UNIT_ID",
            onAdClosed = {
                // Navigate to main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            onAdFailedToLoad = {
                // Skip ad, go to main
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )
    }
}
```

### Native Ad Example
```kotlin
import com.nexax.sdk.api.NativeAdManager

class NativeAdActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        
        val nativeContainer = findViewById<FrameLayout>(R.id.native_container)
        
        NativeAdManager.loadNative(
            activity = this,
            adUnitId = "YOUR_NATIVE_AD_UNIT_ID",
            container = nativeContainer,
            templateType = NativeAdTemplate.MEDIUM // or SMALL, LARGE
        )
    }
}
```

## Bước 5: Handle License Expiry

### Monitor License Status
```kotlin
import com.nexax.sdk.validation.SimpleLicenseValidator

class LicenseChecker {
    
    fun checkLicenseStatus(context: Context) {
        val validator = SimpleLicenseValidator(context)
        val status = validator.getLicenseStatus()
        
        when {
            !status.isCurrentPeriodValid -> {
                Log.w("License", "License expired!")
                // Disable ads, show upgrade message
                disableAdsFeatures()
            }
            status.daysRemaining <= 7 -> {
                Log.w("License", "License expires in ${status.daysRemaining} days")
                // Show renewal reminder
                showRenewalNotification()
            }
            else -> {
                Log.d("License", "License OK, ${status.daysRemaining} days remaining")
            }
        }
    }
    
    private fun disableAdsFeatures() {
        // Hide ad containers
        // Disable ad loading
        // Show "Ads not available" message
    }
    
    private fun showRenewalNotification() {
        // Notify user to contact support for renewal
    }
}
```

## Testing & Debugging

### Test Devices Configuration
SDK đã preconfigured test devices để avoid rate limiting:
- Android Emulator
- Specific device IDs

### Debug Logs
Enable debug để monitor ad loading:
```kotlin
// Check logcat for:
// "AdSDK: License validation successful"
// "AdSDK: Test devices configured"
// "MobileAds initialized"
```

### Common Issues

#### License Validation Failed
```
E/AdSDK: License validation failed: Invalid license key for package: com.your.app
```
**Solution:** 
- Check license key đúng
- Check package name match exactly
- Contact Nexax support

#### No Ads Returned
```
E/AdSDK: No ad to show
```
**Solution:**
- Check internet connection
- Verify ad unit IDs
- Test devices configured properly

#### Firebase Initialization Error
```
E/Firebase: Default FirebaseApp is not initialized
```
**Solution:**
- Add google-services.json
- Add Firebase plugin to build.gradle

## Production Checklist

### Before Release
- [ ] License key implemented correctly
- [ ] Test ads loading on real device  
- [ ] Firebase properly configured
- [ ] Error handling for license failures
- [ ] Backup ads or fallback mechanism
- [ ] License renewal plan setup

### Monitoring
- [ ] Track license status daily
- [ ] Monitor ad performance  
- [ ] Set up alerts for license expiry
- [ ] Regular communication with Nexax team

## Support & Contact

### Nexax Support
- **Email:** support@nexax.global
- **Documentation:** [Partner Portal]
- **Emergency:** [Contact Info]

### Common Support Requests
1. **License Renewal:** Request new key before expiry
2. **Package Change:** Update license for new package name  
3. **Technical Issues:** SDK integration problems
4. **Performance:** Ad loading optimization

---

## Sample Implementation

Tham khảo sample app trong project này để xem implementation hoàn chỉnh:
- `sample-app/src/main/java/com/nexax/sample_app/MainApplication.kt`
- License key setup và error handling
- Ads loading examples

**Happy coding!** 🚀