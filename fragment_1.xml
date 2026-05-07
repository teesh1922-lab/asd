package com.example.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    Fragment1.Fragment1Listener,   // receives name from Fragment1
    Fragment2.Fragment2Listener,   // receives name from Fragment2
    Fragment3.Fragment3Listener    // receives checkbox state from Fragment3
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start with Fragment1
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Fragment1())
                .commit()
        }
    }

    // ── Fragment1Listener ─────────────────────────────────────────────────────
    // Called when user enters name and taps "Get Started" in Fragment1
    override fun onFragment1Continue(name: String) {
        // Pass name to Fragment2 via Bundle/setArguments
        val fragment2 = Fragment2.newInstance(name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment2)
            .addToBackStack(null)
            .commit()
    }

    // ── Fragment2Listener ─────────────────────────────────────────────────────
    // Called when user taps "Continue" in Fragment2
    override fun onFragment2Continue(name: String) {
        // Pass name to Fragment3 via Bundle/setArguments
        val fragment3 = Fragment3.newInstance(name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment3)
            .addToBackStack(null)
            .commit()
    }

    // ── Fragment3Listener ─────────────────────────────────────────────────────
    // Called whenever the checkbox state changes in Fragment3
    override fun onCheckboxStateChanged(isChecked: Boolean) {
        // Host Activity is notified — button logic is handled inside Fragment3
        // You can add analytics, logging, or other side-effects here
    }
}
