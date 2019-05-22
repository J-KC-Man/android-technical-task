package com.example.minimoneybox.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.R
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import com.example.minimoneybox.repository.Repository
import com.example.minimoneybox.viewmodel.UserAccountsViewModel
import com.example.minimoneybox.viewmodel.UserAccountsViewModelFactory

class UserAccountsActivity : AppCompatActivity() {

    private lateinit var factory : UserAccountsViewModelFactory
    private lateinit var viewModel : UserAccountsViewModel

    private lateinit var userAccountsData : AllInvestorProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)

        val bearerToken = intent.extras?.getString("bearerToken")
        Toast.makeText(this, bearerToken, Toast.LENGTH_LONG).show()

        factory = UserAccountsViewModelFactory(Repository(RemoteDataSource(ApiServiceGenerator.createService())))
        viewModel = ViewModelProviders.of(this, factory).get(UserAccountsViewModel::class.java)
        viewModel.makeUserAccountsCall(bearerToken)
        viewModel.userAccountData.observe(this, Observer {
            userAccountsData = it
            setupViews(userAccountsData)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            val intent = Intent(this@UserAccountsActivity, LoginActivity::class.java)
            intent.putExtra("tokenInvalid", it)
            startActivity(intent)
        })
    }

    private fun setupViews(data : AllInvestorProductData) {

    }
}
