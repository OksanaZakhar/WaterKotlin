package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.ActivityAuthenticationBinding

class Authentication : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val tag = "Authentication"
    var isLoginModeActive = false
    lateinit var activityAuthenticationBinding: ActivityAuthenticationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAuthenticationBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_authentication)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this@Authentication, UserInfo::class.java))
        }
    }

    private fun validateEmail(): Boolean {
        val emailInput =
            activityAuthenticationBinding.textInputEmail.editText!!.text.toString().trim()
        return if (emailInput.isEmpty()) {
            activityAuthenticationBinding.textInputEmail.error = "Будь ласка введіть ваш Email"
            false
        } else {
            activityAuthenticationBinding.textInputEmail.error = ""
            true
        }
    }

    private fun validateName(): Boolean {
        val nameInput =
            activityAuthenticationBinding.textInputName.editText!!.text.toString().trim()
        return if (nameInput.isEmpty()) {
            activityAuthenticationBinding.textInputName.error = "Будь ласка введіть ваше ім'я"
            false
        } else if (nameInput.length > 15) {
            activityAuthenticationBinding.textInputName.error =
                "Довжина ім'я має бути не більше ніж 15 символів"
            false
        } else {
            activityAuthenticationBinding.textInputName.error = ""
            true
        }
    }

    private fun validatePassword(): Boolean {
        val passwordInput = activityAuthenticationBinding.textInputPassword.editText!!
            .text.toString().trim()
        return if (passwordInput.isEmpty()) {
            activityAuthenticationBinding.textInputPassword.error = "Введіть пароль"
            false
        } else if (passwordInput.length < 7) {
            activityAuthenticationBinding.textInputPassword.error =
                "Пароль має бути більше 6 символів"
            false
        } else {
            activityAuthenticationBinding.textInputPassword.error = ""
            true
        }
    }

    private fun validateConfirmPassword(): Boolean {
        val passwordInput = activityAuthenticationBinding.textInputPassword.editText!!
            .text.toString().trim()
        val confirmPasswordInput = activityAuthenticationBinding.textInputConfirmPassword.editText!!
            .text.toString().trim()
        return if (passwordInput != confirmPasswordInput) {
            activityAuthenticationBinding.textInputPassword.error = "Паролі не співпадають"
            false
        } else {
            activityAuthenticationBinding.textInputPassword.error = ""
            true
        }
    }

    fun loginSingUpUser(view: View?) {
        if (!validateEmail() or !validateName() or !validatePassword()) {
            return
        }
        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(
                activityAuthenticationBinding.textInputEmail.editText!!.text.toString().trim(),
                activityAuthenticationBinding.textInputPassword.editText!!.text.toString().trim()
            )
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "signInWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        Log.w(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@Authentication, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            if (!validateEmail() or !validateName() or !validatePassword() or
                !validateConfirmPassword()
            ) {
                return
            }
            auth.createUserWithEmailAndPassword(
                activityAuthenticationBinding.textInputEmail.editText!!.text.toString().trim(),
                activityAuthenticationBinding.textInputPassword.editText!!.text.toString().trim()
            )
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        Log.w(
                            tag,
                            "createUserWithEmail:failure",
                            task.exception
                        )
                        Toast.makeText(
                            this@Authentication, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        startActivity(Intent(this@Authentication, MainActivity::class.java))
    }

    fun toggleLoginSingUp(view: View?) {
        if (isLoginModeActive) {
            isLoginModeActive = false
            activityAuthenticationBinding.loginSingUpButton.text = "Зареєструватися"
            activityAuthenticationBinding.toggleLoginSingUpTextView.text = "Або війти"
            activityAuthenticationBinding.textInputConfirmPassword.visibility = View.VISIBLE
        } else {
            isLoginModeActive = true
            activityAuthenticationBinding.loginSingUpButton.text = "Увійти"
            activityAuthenticationBinding.toggleLoginSingUpTextView.text = "Або зареєструватися"
            activityAuthenticationBinding.textInputConfirmPassword.visibility = View.GONE
        }
    }
}