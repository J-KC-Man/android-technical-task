package com.example.minimoneybox

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import java.util.regex.Pattern

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var btn_sign_in : Button
    lateinit var til_email : TextInputLayout
    lateinit var et_email : EditText
    lateinit var til_password : TextInputLayout
    lateinit var et_password : EditText
    lateinit var til_name : TextInputLayout
    lateinit var et_name : EditText
    lateinit var pigAnimation : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    private fun setupViews() {
        btn_sign_in = findViewById(R.id.btn_sign_in)
        til_email = findViewById(R.id.til_email)
        et_email = findViewById(R.id.et_email)
        til_password = findViewById(R.id.til_password)
        et_password = findViewById(R.id.et_password)
        til_name = findViewById(R.id.til_name)
        et_name = findViewById(R.id.et_name)
        pigAnimation = findViewById(R.id.animation)

        btn_sign_in.setOnClickListener {
            if (allFieldsValid()) {
                Toast.makeText(this, R.string.input_valid, Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun validateEmailField() : Boolean {
        val emailInput = et_email.text.toString().trim()
        var isEmailValid = false

        if(emailInput.isEmpty()) {
            til_email.error = getString(R.string.email_address_empty)
        } else if(!emailInput.matches(EMAIL_REGEX)) {
            til_email.error = getString(R.string.email_address_error)
        } else {
            til_email.error = null
            isEmailValid = true
        }

        return isEmailValid
    }

    private fun validatePasswordField() : Boolean {
        val passwordInput = et_password.text.toString().trim()
        var isPasswordValid = false

        if(passwordInput.isEmpty()) {
            til_password.error = getString(R.string.password_empty)
        } else if(!passwordInput.matches(PASSWORD_REGEX)) {
            til_password.error = getString(R.string.password_error)
        } else {
            til_password.error = null
            isPasswordValid = true
        }

        return isPasswordValid
    }

    private fun validateNameField() : Boolean {
        val nameInput = et_name.text.toString().trim()
        var isNameValid = false

        if(nameInput.isNotEmpty() and !nameInput.matches(NAME_REGEX)) {
            til_name.error = getString(R.string.full_name_error)
        } else {
            til_name.error = null
            isNameValid = true
        }

        return isNameValid
    }

    private fun allFieldsValid() : Boolean {

        if (!validateEmailField() or !validatePasswordField() or !validateNameField()) {
            return false
        }
        return true
    }

    private fun setupAnimation() {
        pigAnimation.setMinAndMaxFrame(firstAnim.first, firstAnim.second)
        pigAnimation.playAnimation()

        pigAnimation.addAnimatorUpdateListener {
            if (pigAnimation.frame == firstAnim.second) {
                pigAnimation.setMinAndMaxFrame(secondAnim.first, secondAnim.second)
                pigAnimation.repeatCount = LottieDrawable.INFINITE
            }
        }
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+".toRegex()
        val NAME_REGEX = "[a-zA-Z]{6,30}".toRegex()
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$".toRegex()
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }
}
