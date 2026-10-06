plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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
//        buildConfigField("String", "interSplash2ID", "\"ca-app-pub-6676377890358166/3109964123\"")
//        buildConfigField("String", "interSplash", "\"ca-app-pub-6676377890358166/7899256371\"")
//        buildConfigField("String", "nativeSplash2ID", "\"ca-app-pub-6676377890358166/4558954773\"")
//        buildConfigField("String", "bannerSplash", "\"ca-app-pub-6676377890358166/2790399923\"")
        buildConfigField("String", "interSplash2ID", "\"ca-app-pub-3940256099942544/1033173712\"")
        buildConfigField("String", "interSplash", "\"ca-app-pub-3940256099942544/1033173712\"")
        buildConfigField("String", "nativeSplash2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "bannerSplash", "\"ca-app-pub-3940256099942544/9214589741\"")

        // ============================================
        // LFO Ad Units
        // ============================================
//        buildConfigField("String", "lfo1Native2ID", "\"ca-app-pub-6676377890358166/1142276339\"")
//        buildConfigField("String", "lfo1Native", "\"ca-app-pub-6676377890358166/7324541303\"")
//        buildConfigField("String", "lfo2Native2ID", "\"ca-app-pub-6676377890358166/7910516589\"")
//        buildConfigField("String", "lfo2Native", "\"ca-app-pub-6676377890358166/7008498452\"")
        buildConfigField("String", "lfo1Native2ID", "\"ca-app-pub-3940256099942544/1044960115--\"")
        buildConfigField("String", "lfo1Native", "\"ca-app-pub-3940256099942544/1044960115--\"")
        buildConfigField("String", "lfo2Native2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "lfo2Native", "\"ca-app-pub-3940256099942544/1044960115\"")

        // ============================================
        // OnBoarding Ad Units
        // ============================================
//        buildConfigField("String", "ob1Native2ID", "\"ca-app-pub-6676377890358166/3054123800\"")
//        buildConfigField("String", "ob1Native", "\"ca-app-pub-6676377890358166/9465804700\"")
//        buildConfigField("String", "obFull1Native2ID", "\"ca-app-pub-6676377890358166/7979147420\"")
//        buildConfigField("String", "obFull1Native", "\"ca-app-pub-6676377890358166/4367383087\"")
//        buildConfigField("String", "ob3Native", "\"ca-app-pub-6676377890358166/6203208930\"")
        buildConfigField("String", "ob1Native2ID", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "ob1Native", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "obFull1Native2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "obFull1Native", "\"ca-app-pub-3940256099942544/1044960115\"")
        buildConfigField("String", "ob3Native", "\"ca-app-pub-3940256099942544/2247696110\"")

        // ============================================
        // Full Ad Units
        // ============================================
        buildConfigField("String", "fuAf02Na2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "fuAf02Na", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "fuAf033Na2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "fuAf033Na", "\"ca-app-pub-3940256099942544/2247696110\"")

        // ============================================
        // Survey Ad Units [NEW]
        // ============================================
        buildConfigField("String", "surveyNative2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "surveyNative", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "af04Inter2ID", "\"ca-app-pub-3940256099942544/10331737121\"")
        buildConfigField("String", "af04Inter", "\"ca-app-pub-3940256099942544/1033173712\"")


        // ============================================
        // Open Resume Ad Units [NEW]
        // ============================================
        buildConfigField("String", "openResume2ID", "\"ca-app-pub-3940256099942544/9257395921\"")
        buildConfigField("String", "openResume", "\"ca-app-pub-3940256099942544/9257395921\"")
        buildConfigField("String", "interResume2ID", "\"ca-app-pub-3940256099942544/1033173712\"")


        // ============================================
        // In-app Ad Units
        // ============================================
        buildConfigField("String", "nativeHomeTest2ID", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "nativeHomeTest", "\"ca-app-pub-3940256099942544/2247696110\"")
        buildConfigField("String", "interHomeTest2ID", "\"ca-app-pub-3940256099942544/1033173712\"")
        buildConfigField("String", "interHomeTest", "\"ca-app-pub-3940256099942544/1033173712\"")
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
        buildConfig = true
        viewBinding = true
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(files("libs/ads-sdk-release-new.aar"))

    // Balloon
    implementation("com.github.skydoves:balloon:1.7.6")

    // Glide
    implementation("com.github.bumptech.glide:glide:5.0.7")

    implementation ("com.squareup.okhttp3:okhttp:5.3.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation ("org.json:json:20251224")

    // ADJUST
    implementation ("com.adjust.sdk:adjust-android:5.8.0")

    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.android.installreferrer:installreferrer:2.2")

    implementation ("com.google.android.ump:user-messaging-platform:4.0.0")
    implementation ("com.google.android.gms:play-services-ads:24.9.0")
    implementation ("com.google.ads.mediation:facebook:6.21.0.2")
    implementation ("com.facebook.android:audience-network-sdk:6.21.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-config")
    implementation ("com.google.firebase:firebase-common")

    implementation("com.google.firebase:firebase-ai")

    // Ad Shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    // Dots Indicator
    implementation ("com.tbuonomo:dotsindicator:5.1.0")

    // Lottie
    implementation ("com.airbnb.android:lottie:6.7.1")

    // GSON
    implementation("com.google.code.gson:gson:2.14.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}