package com.example.minimoneybox.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.R
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import com.example.minimoneybox.repository.Repository
import com.example.minimoneybox.viewmodel.userAccounts.UserAccountsViewModel
import com.example.minimoneybox.viewmodel.userAccounts.UserAccountsViewModelFactory
import kotlinx.android.synthetic.main.activity_user_accounts.*

class UserAccountsActivity : AppCompatActivity() {

    private lateinit var factory : UserAccountsViewModelFactory
    private lateinit var viewModel : UserAccountsViewModel

    private lateinit var userAccountsData : AllInvestorProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        setSupportActionBar(findViewById(R.id.user_accounts_toolbar))

        val bearerToken = getSavedData(BEARER_TOKEN_DEFAULT_SHARED_PREF)

        factory = UserAccountsViewModelFactory(
            Repository(
                RemoteDataSource(ApiServiceGenerator.createService())
            )
        )
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

    private fun getSavedData(key: String) : String? =
         PreferenceManager
            .getDefaultSharedPreferences(this)
            .getString(key, "")


    private fun setupViews(data : AllInvestorProductData) {
        val userName = getSavedData(USERNAME_DEFAULT_SHARED_PREF)
        greeting_textView.text = getString(R.string.greeting_message, userName)
        planValue_textView.text = getString(R.string.total_value, data.TotalPlanValue)
        account1_accountName_textView.text = data.ProductResponses[0].Product.FriendlyName
        account1_accountDetails_textView.text = getString(
            R.string.account_detail,
            data.ProductResponses[0].PlanValue,
            data.ProductResponses[0].Moneybox)

        account2_accountName_textView.text = data.ProductResponses[1].Product.FriendlyName
        account2_accountDetails_textView.text = getString(
            R.string.account_detail,
            data.ProductResponses[1].PlanValue,
            data.ProductResponses[1].Moneybox)

        account3_accountName_textView.text = data.ProductResponses[2].Product.FriendlyName
        account3_accountDetails_textView.text = getString(
            R.string.account_detail,
            data.ProductResponses[2].PlanValue,
            data.ProductResponses[2].Moneybox)
    }
}
