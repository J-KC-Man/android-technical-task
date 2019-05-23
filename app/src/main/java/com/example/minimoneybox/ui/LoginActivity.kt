package com.example.minimoneybox.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieDrawable
import com.example.minimoneybox.R
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.repository.Repository
import com.example.minimoneybox.viewmodel.login.LoginViewModel
import com.example.minimoneybox.viewmodel.login.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

const val BEARER_TOKEN_DEFAULT_SHARED_PREF = "bearer_token"
const val USERNAME_DEFAULT_SHARED_PREF = "username"
/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var factory : ViewModelFactory
    private lateinit var viewModel : LoginViewModel

    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        hideProgressBar()
        // Tell the user if token has become invalid
        val message = intent.extras?.getString("tokenInvalid")
        if(!message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        factory =
            ViewModelFactory(Repository(RemoteDataSource(ApiServiceGenerator.createService())))
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.bearerToken.observe(this, Observer {
            btn_sign_in.isEnabled = true
            hideProgressBar()
            saveData(sharedPreferences, it, et_name.text.toString())

            val intent = Intent(this@LoginActivity, UserAccountsActivity::class.java)
            startActivity(intent)
        })

        viewModel.error.observe(this, Observer {
            btn_sign_in.isEnabled = true
            hideProgressBar()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        Log.i("pigAnimation","animation started")
        setupAnimation()
    }

    private fun saveData(sharedPreferences: SharedPreferences, bearerToken: String, name: String) {
        sharedPreferences
            .edit()
            .putString(BEARER_TOKEN_DEFAULT_SHARED_PREF, bearerToken)
            .putString(USERNAME_DEFAULT_SHARED_PREF, name)
            .apply()
    }

    private fun setupViews() {

        btn_sign_in.setOnClickListener {
            if (allFieldsValid()) {
                btn_sign_in.isEnabled = false
                login_progressBar.visibility = View.VISIBLE
                viewModel.makeLoginCall(et_email.text.toString().trim(), et_password.text.toString().trim())
            }
        }
    }

    private fun hideProgressBar() {
        login_progressBar.visibility = View.INVISIBLE
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
        animation.setMinAndMaxFrame(firstAnim.first, firstAnim.second)
        animation.playAnimation()

        animation.addAnimatorUpdateListener {
            if (animation.frame == firstAnim.second) {
                animation.setMinAndMaxFrame(secondAnim.first, secondAnim.second)
                animation.repeatCount = LottieDrawable.INFINITE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("pigAnimation","animation paused")
        animation.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        Log.i("pigAnimation","animation resumed")
        animation.resumeAnimation()
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+".toRegex()
        val NAME_REGEX = "[a-zA-Z]{6,30}".toRegex()
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$".toRegex()
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }
}
