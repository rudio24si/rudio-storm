package com.example.rudio_storm.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.rudio_storm.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.onboardingDotsIndicator)

        // Memasang adapter baru yang sudah di-rename
        val adapter = OnboardingFragmentAdapter(this)
        viewPager.adapter = adapter

        // Menghubungkan dots indicator dengan view pager onboarding
        dotsIndicator.setViewPager2(viewPager)
    }
}