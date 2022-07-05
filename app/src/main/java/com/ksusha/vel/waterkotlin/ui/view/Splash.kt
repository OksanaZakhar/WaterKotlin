package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ksusha.vel.waterkotlin.R

class Splash : AppCompatActivity() {

    private val SPLASH_DISPLAY = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@Splash, MainActivity::class.java)
            this@Splash.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY.toLong())
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}