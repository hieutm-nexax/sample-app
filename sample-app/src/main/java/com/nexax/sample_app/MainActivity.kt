package com.nexax.sample_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nexax.sdk.api.AdSDK
import com.nexax.sdk.api.activity.NexaxBaseActivity

class MainActivity : NexaxBaseActivity() {
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Test Ad Inspector - tap screen 10 times
        var tapCount = 0
        findViewById<android.view.View>(R.id.main).setOnClickListener {
            tapCount++
            if (tapCount >= 10) {
                Toast.makeText(this, "Opening Ad Inspector (test)...", Toast.LENGTH_SHORT).show()
                Log.e("TEST_INSPECTOR", "Attempting to open Ad Inspector...")
                AdSDK.openAdInspector(this)
                tapCount = 0
            }
        }

        findViewById<AppCompatButton>(R.id.button_load_ad).setOnClickListener {
            preloadAd()
        }

        findViewById<AppCompatButton>(R.id.button_show_ad).setOnClickListener {
            clickCount += 1
            showAd()
        }
    }

    private fun preloadAd() {
        AdSDK.loadAd("home_test")
    }

    private fun showAd() {
        AdSDK.showAd(
            adName = "home_test",
            activity = this,
            fragmentManager = supportFragmentManager,
            containerId = R.id.layout_full_ad,
            clickCount = clickCount,
            onNextEvent = {}
        )
    }
}
