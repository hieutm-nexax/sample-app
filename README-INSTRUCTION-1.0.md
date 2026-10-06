# [Nexax] Tutorial SDK: Version 1.0 *(Latest version)*

> **For New Developers**: This is a step-by-step guide to integrate the `AD-SDK` into your Android app.
   
   
## Table of Contents
1. [What is NexaX Ad SDK?](#what-is-nexax-ad-sdk)
2. [Prerequisites](#prerequisites)
3. [Step 1: Project Setup](#step-1-project-setup)
4. [Step 2: Add Dependencies](#step-2-add-dependencies)
5. [Step 3: Modify Layout files](#step-3-modify-layout-files)
6. [Step 4: Initialize SDK in Application](#step-4-initialize-sdk-in-application)
7. [Step 5: Create Activity Files](#step-5-create-activity-files)
8. [Step 6: Configure AndroidManifest](#step-6-configure-androidmanifest)
9. [Step 7: Add Ad Unit IDs](#step-7-add-ad-unit-ids)
10. [Step 8: Setup Firebase](#step-8-setup-firebase)
11. [Step 9: Track Subscription Purchases](#step-9-track-subscription-purchases)
12. [Step 10: Test Your Implementation](#step-10-test-your-implementation)


---

## What is NexaX Ad SDK?

The NexaX AD SDK is a pre-built solution that manages your app's first-time user experience, including:
- **Splash Screen** with ads (Banner, Native, Interstitial)
- **Language Selection** (LFO - Language First Open)
- **Onboarding Screens** with native ads
- **Firebase Remote Config** integration for A/B testing
- **Billing and Subscription** support

---

## Prerequisites

Before starting, make sure you have:

✅ **Android Studio** (Arctic Fox or newer)
✅ **Kotlin** knowledge (basic)
✅ **Firebase Account** (for Remote Config and Analytics)
✅ **Google AdMob Account** (for ad units)
✅ **SDK AAR file** (provided by NexaX)

---

## Step 1: Project Setup

Create **`app/libs`** folder, then add the **`ads-sdk-release.aar`** _(Our SDK AAR)_ file to it.


---

## Step 2: Add Dependencies

Update **`app/build.gradle.kts`**:

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nexax.sample_app"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.nexax.sample_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ============================================
        // Splash Ad Units
        // ============================================
        buildConfigField("String", "interSplash2ID", "\"ca-app-pub-3940256099942544/1033173712\"")
        buildConfigField("String", "interSplash", "\"ca-app-pub-3940256099942544/1033173712\"")
        buildConfigField("String", "nativeSplash2ID", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "bannerSplash", "\"ca-app-pub-3940256099942544/9214589741\"")

        // ============================================
        // LFO Ad Units
        // ============================================
        buildConfigField("String", "lfo1Native2ID", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "lfo1Native", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "lfo2Native2ID", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "lfo2Native", "\"ca-app-pub-3940256099942544/2247696110\"")

        // ============================================
        // OnBoarding Ad Units
        // ============================================
        buildConfigField("String", "ob1Native2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "ob1Native", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "obFull1Native2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "obFull1Native", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "ob3Native", "\"ca-app-pub-3940256099942544/2247696110\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true      // IMPORTANT FOR AD UNIT IDs
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(files("libs/ads-sdk-release.aar"))

    // ADJUST
    implementation ("com.adjust.sdk:adjust-android:5.5.0")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.adjust.sdk:adjust-android:5.0.0") {
        exclude(group = "com.adjust.signature", module = "adjust-android-signature")
    }
    implementation ("com.adjust.signature:adjust-android-signature:3.62.0")

    implementation ("com.google.android.ump:user-messaging-platform:4.0.0")
    implementation ("com.google.android.gms:play-services-ads:24.9.0")
    implementation ("com.google.ads.mediation:facebook:6.21.0.0")
    implementation ("com.facebook.android:audience-network-sdk:6.21.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-config-ktx")
    implementation ("com.google.firebase:firebase-common-ktx")

    // Ad Shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    // Dots Indicator
    implementation ("com.tbuonomo:dotsindicator:5.1.0")

    // Lottie
    implementation ("com.airbnb.android:lottie:6.7.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
```

---

## Step 3: Modify Layout files

Your task is only to create the layout file for the Splash Screen.
Other screens (LFO, Onboarding, Preparing Data) are already implemented and can be customized by changing the UI component colors.

### 3.1 Splash Screen Layout

Here is our suggestion for your splash layout
Create **`res/layout/activity_splash`**:

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/bg_screen"
    tools:context=".SplashActivity">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginBottom="40dp"
        android:orientation="vertical"
        android:gravity="center">

        <ImageView
            android:id="@+id/app_logo"
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:src="@drawable/logo_sample"
            android:importantForAccessibility="no"/>

        <TextView
            android:id="@+id/app_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="NexaX Global"
            android:textSize="20sp"
            android:textColor="@color/black"
            android:textStyle="bold"
            tools:ignore="HardcodedText" />
    </LinearLayout>

</FrameLayout>
```

### 3.2 Customize App Themes

If your app supports Light and Dark modes, we recommend creating two `themes.xml` files for each mode.

Modify **`res/values/themes.xml`**:

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Base.Theme.SdkAds" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="color_bg_screen">@color/bg_screen</item>
        <item name="color_bg_item_language">@color/bg_item</item>
        <item name="color_text_level_1">@color/text_color</item>
        <item name="color_text_level_2">@color/subtext_color</item>
        <item name="color_icon">@color/bg_blue</item>

        <item name="color_bg_ad">@color/bg_native</item>
        <item name="color_bg_ad_cta">@color/bg_blue</item>
        <item name="color_text_ad_cta">@color/white</item>
        <item name="color_text_ad">@color/text_color</item>
    </style>
</resources>
```

**Note:** For each `item`, you can pass value from `colors.xml` file or color hex code.

---

## Step 4: Initialize SDK in Application

### 4.1 Create Application Class

Create **`MyApplication.kt`** in your package:

```kotlin
package com.yourcompany.yourapp

import android.app.Application
import android.util.Log
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.google.firebase.FirebaseApp
import com.nexax.sdk.api.AdSDK
import com.nexax.sdk.internal.adjust.AdjustTracker

class MainApplication: Application() {
    // License key generated for com.nexax.sample_app package (exact from logs)
    private val LICENSE_KEY = "YOUR_LICENSE_KEY_HERE"

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        // Init Adjust
        AdSDK.initAdjust(
            application = this,
            appToken = "YOUR_APP_TOKEN_HERE",
            environment = AdjustConfig.ENVIRONMENT_SANDBOX,
            logLevel = LogLevel.VERBOSE,
            fbAppId = "YOUR_META_APP_ID_HERE"
        )

        // Initialize Nexax SDK with license validation
        try {
            Log.d("MainApplication", "Initializing Nexax SDK with license...")
            AdSDK.initialize(this, LICENSE_KEY)
            Log.d("MainApplication", "Nexax SDK initialized successfully")
        } catch (e: SecurityException) {
            Log.e("MainApplication", "License validation failed: ${e.message}")
            // In production, you might want to disable ads functionality or show error message
        } catch (e: Exception) {
            Log.e("MainApplication", "Failed to initialize Nexax SDK", e)
        }

        // Initialize event token
        AdjustTracker.setEventToken(
            rev = "YOUR_AD_REVENUE_TOKEN",
            purchase = "YOUR_PURCHASE_TOKEN"
        )
    }
}
```

**Replace:** 
+ `YOUR_LICENSE_KEY_HERE` with the **License Key** provided by our team (please contact us to request access).
+ `YOUR_APP_TOKEN_HERE` with the **App Token** generated by our system.
+ `YOUR_META_APP_ID_HERE` with the **Meta App Id** generated by our system.
+ `YOUR_AD_REVENUE_TOKEN` with the **Ad Revenue Event Token** provided by us.
+ `YOUR_PURCHASE_TOKEN` with the **Purchase Event Token** provided by us.

**Caution:** Once you’ve completed your testing, build your app for production by setting the **environment** to **AdjustConfig.ENVIRONMENT_PRODUCTION**

---

## Step 5: Create Activity Files

### 5.1 Create Splash Activity

Create **`SplashActivity.kt`** that extends from ***`NexaxSplashActivity`***:

```kotlin
package com.yourcompany.yourapp

import android.content.Intent
import com.nexax.sdk.api.model.AdUnitIds
import com.nexax.sdk.internal.ui.NexaxSplashActivity
import com.nexax.sdk.internal.ui.model.AppConfig

class SplashActivity : NexaxSplashActivity() {
    // ============================================
    // REQUIRED METHOD 1: Layout Resource
    // ============================================
    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    // ============================================
    // REQUIRED METHOD 2: Ad Configuration
    // ============================================
    override fun adConfig(): AdUnitIds {
        return AdUnitIds(
            interSplash2ID = BuildConfig.interSplash2ID,
            interSplash = BuildConfig.interSplash,
            nativeSplash2ID = BuildConfig.nativeSplash2ID,
            bannerSplash = BuildConfig.bannerSplash,

            lfo1Native2ID = BuildConfig.lfo1Native2ID,
            lfo1Native = BuildConfig.lfo1Native,
            lfo2Native2ID = BuildConfig.lfo2Native2ID,
            lfo2Native = BuildConfig.lfo2Native,

            ob1Native2ID = BuildConfig.ob1Native2ID,
            ob1Native = BuildConfig.ob1Native,
            obFull1Native2ID = BuildConfig.obFull1Native2ID,
            obFull1Native = BuildConfig.obFull1Native,
            ob3Native = BuildConfig.ob3Native
        )
    }

    // ============================================
    // REQUIRED METHOD 3: App Configuration
    // ============================================
    override fun setAppConfig(): AppConfig {
        return AppConfig(
            showNavigationBar = false,
            showSystemBar = false,
            themeMode = AppConfig.ThemeMode.DARK_MODE
        )
    }

    // ============================================
    // REQUIRED METHOD 4: Splash Duration
    // ============================================
    override fun getSplashDurationMillis(): Long {
        return 5000L
    }

    // ============================================
    // REQUIRED METHOD 5: LFO Screen Navigation
    // ============================================
    override fun toLanguageScreen() {
        val intent = Intent(this, LFOActivity::class.java)
        startActivity(intent)
        finish()
    }

    // ============================================
    // REQUIRED METHOD 6: OnBoarding Screen Navigation
    // ============================================
    override fun toOnBoardingScreen() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    // ============================================
    // REQUIRED METHOD 7: Home Screen Navigation
    // ============================================
    override fun toHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
```

### 5.2 Create LFO Activity

Create **`LFOActivity.kt`** that extends from ***`NexaxLFOActivity`***:

```kotlin
package com.yourcompany.yourapp

import android.content.Intent
import com.nexax.sdk.internal.ui.NexaxLFOActivity

class LFOActivity : NexaxLFOActivity() {
    // ============================================
    // REQUIRED METHOD 1: List of Language codes
    // ============================================
    override fun getListLanguageCode(): List<String> {
        return listOf(
            "en",
            "zh",
            "es",
            "fi",
            "fr",
            "it",
            "ko",
            "ja",
            "th",
            "vi"
        )
    }

    // ============================================
    // REQUIRED METHOD 2: OnBoarding Screen Navigation
    // ============================================
    override fun nextScreen() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
```

### 5.3 Create OnBoarding Activity

Create **`OnBoardingActivity.kt`** that extends from ***`NexaxOnboardingActivity`***:

```kotlin
package com.yourcompany.yourapp

import android.content.Intent
import com.nexax.sdk.internal.adapter.model.OnboardingPage
import com.nexax.sdk.internal.ui.NexaxOnboardingActivity

class OnBoardingActivity : NexaxOnboardingActivity() {
    // ============================================
    // REQUIRED METHOD 1: List of OnBoarding Items
    // ============================================
    override fun getOnboardingList(): List<OnboardingPage> {
        return listOf(
            OnboardingPage.Onboarding(
                R.drawable.ob_1,
                "Feature 1: OnBoarding 1",
                "This is the first feature of our app"
            ),
            OnboardingPage.Onboarding(
                R.drawable.ob_2,
                "Feature 2: OnBoarding 2",
                "This is the second feature of our app"
            ),
            OnboardingPage.Onboarding(
                R.drawable.ob_3,
                "Feature 3: OnBoarding 3",
                "This is the third feature of our app"
            )
        )
    }

    // ============================================
    // REQUIRED METHOD 2: Home Screen Navigation
    // ============================================
    override fun directToNextScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
```

---

## Step 6: Configure AndroidManifest

Update **`AndroidManifest.xml`**:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SdkAds">
        
        <!-- AdMob App ID (REQUIRED) -->
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-xxxxx~xxxxx" />

        <!-- Splash Activity - LAUNCHER -->
        <activity
            android:name=".SplashActivity"
            android:exported="true"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- LFO Activity -->
        <activity
            android:name=".LFOActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- OnBoarding Activity -->
        <activity
            android:name=".OnBoardingActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- Main Activity -->
        <activity
            android:name=".MainActivity"
            android:exported="false"
            android:screenOrientation="portrait" />
    </application>

</manifest>
```

**Replace** `ca-app-pub-xxxxx~xxxxx` with your **actual AdMob App ID**.

---

## Step 7: Add Ad Unit IDs

```kotlin
COMMING SOON
```

---

## Step 8: Setup Firebase

### 8.1 Add google-services.json

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select existing
3. Add your Android app
4. Download `google-services.json`
5. Place it in **`app/`** directory

### 8.2 Configure Remote Config (Optional)

In Firebase Console → Remote Config, add these keys:

```
// Splash keys
fo_inter_splash: true
fo_hf_inter_splash: true
fo_logic_ad_launcher: in_then_na
fo_timeout_ad_splash_2id: 60
fo_timeout_ad_splash: 60
fo_banner_splash: true
fo_countdown_na_launcher: 10
fo_countdown_na_launcher_alt: 5
logic_na_ful_alt: false
logic_na_ful_alt_ID: new

// LFO keys
logic_lfo: true
fo_logic_inter_splash_inc_impression: true

// OnBoarding keys
fo_native_full_scr1: true
fo_native_full_scr2: true
fo_hf_native_full_scr1: true
fo_hf_native_full_scr2: true
fo_time_auto_next_full_scr: 15

// Other keys
fo_refill_by_ad: true
fo_time_close_native_full: 10
fo_time_next_preparing: 3
fo_native_full_preparing_data: true
fo_native_full_preparing_data_2ID: false
```

---

## Step 9: Track Subscription Purchases

When the billing callback confirms that a purchase has been successfully acknowledged,
you must track the subscription purchase at this point.

Please call the `trackSubscription()` function inside the `onPurchasesUpdated()` callback
to ensure the transaction is recorded correctly.

```kotlin
import com.nexax.sdk.internal.adjust.AdjustTracker

override fun onPurchasesUpdated(
    billingResult: BillingResult,
    purchases: MutableList<Purchase>?
) {
    if (billingResult.responseCode != BillingClient.BillingResponseCode.OK || purchases == null) return

    for (purchase in purchases) {
        if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED) continue

        if (!purchase.isAcknowledged) {
            billingClient.acknowledgePurchase(
                AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
            ) { ackResult ->
                if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    AdjustTracker.trackSubscription(
                        price = pricingPhase.priceAmountMicros,
                        currency = pricingPhase.priceCurrencyCode,
                        sku = productDetails.productId,
                        orderId = purchase.orderId,
                        signature = purchase.signature,
                        purchaseToken = purchase.purchaseToken,
                        purchaseTime = purchase.purchaseTime
                    )
                }
            }
        }
    }
}
```

---

## Step 10: Test Your Implementation

### 10.1 Build and Run

1. **Sync Gradle** (Build → Make Project)
2. **Run on Device** or Emulator (minSdk 24+)
3. **Watch Logcat** for "AD-SDK" logs

### 10.2 Testing Flow

Expected flow:
1. ✅ Splash screen appears
2. ✅ Banner/native ad loads (if enabled)
3. ✅ Interstitial ad shows after 2-3 seconds
4. ✅ Language selection screen appears
5. ✅ Select a language → Continue
6. ✅ Onboarding screens with ads
7. ✅ Navigate to MainActivity

### 10.3 Reset Flow for Testing

To test again:
1. Clear app data: Settings → Apps → Your App → Clear Data
2. Or uninstall then reinstall

---