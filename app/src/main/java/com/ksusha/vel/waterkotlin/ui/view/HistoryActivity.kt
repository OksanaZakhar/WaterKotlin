package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ksusha.vel.waterkotlin.R

class HistoryActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var toMain: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        toMain = findViewById<View>(R.id.goToMain) as FloatingActionButton
        toMain.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.goToMain -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else -> {}
        }
    }
}