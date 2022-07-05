package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.ActivityUserInfoBinding

class UserInfo : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var activityUserInfoBinding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        auth = FirebaseAuth.getInstance()

        activityUserInfoBinding.userEmail.text = auth.currentUser!!.email
        activityUserInfoBinding.startPage.setOnClickListener {
            startActivity(
                Intent(
                    this@UserInfo,
                    MainActivity::class.java
                )
            )
        }

        activityUserInfoBinding.singOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@UserInfo, Authentication::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@UserInfo, MainActivity::class.java))
    }

}