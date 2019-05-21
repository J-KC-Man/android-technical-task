package com.example.minimoneybox.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.minimoneybox.R

class UserAccountsActivity : AppCompatActivity() {

   // private lateinit var bearerToken : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)

        val bearerToken = intent.extras?.getString("bearerToken")
        Toast.makeText(this, bearerToken, Toast.LENGTH_LONG).show()
    }
}
