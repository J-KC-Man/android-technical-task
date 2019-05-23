package com.example.minimoneybox.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.R
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.repository.Repository
import com.example.minimoneybox.viewmodel.quickPayment.QuickPaymentViewModel
import com.example.minimoneybox.viewmodel.quickPayment.QuickPaymentViewModelFactory
import kotlinx.android.synthetic.main.activity_one_off_payment.*

class OneOffPaymentActivity : AppCompatActivity() {

    private lateinit var factory : QuickPaymentViewModelFactory
    private lateinit var viewModel : QuickPaymentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_off_payment)
        setSupportActionBar(findViewById(R.id.user_accounts_toolbar))

        val bearerToken = getSavedData(BEARER_TOKEN_DEFAULT_SHARED_PREF)
        val accountName = intent.extras?.getString("accountName")
        val planValue = intent.extras?.getString("planValue")
        val moneyBox = intent.extras?.getString("moneyBox")
        val investorProductId = intent.extras?.getString("investorProductId")

        factory = QuickPaymentViewModelFactory(Repository(RemoteDataSource(ApiServiceGenerator.createService())))
        viewModel = ViewModelProviders.of(this, factory).get(QuickPaymentViewModel::class.java)

        account_name_textView.text = accountName
        plan_value_textView.text = getString(R.string.plan_value, planValue)
        moneybox_textView.text = getString(R.string.moneybox, moneyBox)
        payment_btn.text = getString(R.string.quick_deposit)

        payment_btn.setOnClickListener {
            viewModel.makePaymentCall(bearerToken, "10", investorProductId)
        }

        viewModel.quickPaymentResult.observe(this, Observer {
            val newAmount = moneyBox!!.toDouble() + it.toDouble()
            moneybox_textView.text = getString(R.string.moneybox, newAmount.toString())
        })

    }

    private fun getSavedData(key: String) : String? =
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .getString(key, "")

}
