package com.nexax.sample_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nexax.sdk.api.AdSDK
import com.nexax.sdk.api.activity.NexaxLFOActivity

class LFOActivity : NexaxLFOActivity() {
    private var tapCount = 0
    private var lastTapTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("TAG__LFO_LIFECYCLE", "onCreate: ")

        // Add tap-to-open Ad Inspector (10 taps within 3 seconds)
        window.decorView.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTapTime > 3000) {
                tapCount = 0
            }
            lastTapTime = currentTime
            tapCount++

            Log.e("LFO_TEST", "Tap count: $tapCount")

            if (tapCount >= 10) {
                Toast.makeText(this, "🔍 Opening Ad Inspector...", Toast.LENGTH_SHORT).show()
                Log.e("LFO_TEST", "Opening Ad Inspector via tap")
                AdSDK.openAdInspector(this)
                tapCount = 0
            }
        }
    }

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

    override fun nextScreen() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
    }

    override fun isShowTooltip(): Boolean {
        return true
    }

    override fun onStart() {
        Log.i("TAG__LFO_LIFECYCLE", "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        Log.i("TAG__LFO_LIFECYCLE", "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Log.i("TAG__LFO_LIFECYCLE", "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.i("TAG__LFO_LIFECYCLE", "onStop: ")
        super.onStop()
    }
}