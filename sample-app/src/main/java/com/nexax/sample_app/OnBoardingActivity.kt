package com.nexax.sample_app

import android.content.Intent
import com.nexax.sdk.api.activity.NexaxOnboardingActivity
import com.nexax.sdk.api.model.OBPage

class OnBoardingActivity : NexaxOnboardingActivity() {
    override fun getOnboardingList(): List<OBPage> {
        return listOf(
            OBPage.Onboarding(
                R.drawable.ob_1,
                getString(R.string.feat_1),
                getString(R.string.feat_1_des)
            ),
            OBPage.Onboarding(
                R.drawable.ob_2,
                getString(R.string.feat_2),
                getString(R.string.feat_2_des)
            ),
            OBPage.Onboarding(
                R.drawable.ob_3,
                getString(R.string.feat_3),
                getString(R.string.feat_3_des)
            )
        )
    }

    override fun directToNextScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}