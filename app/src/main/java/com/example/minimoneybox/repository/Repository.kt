package com.example.minimoneybox.repository

import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.Result
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import com.example.minimoneybox.datasource.model.payment.PaymentResponse

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun login(email : String, password: String) : Result<String> =
        remoteDataSource.loginUser(email, password)


    suspend fun getInvestorProducts(bearerToken : String?) : Result<AllInvestorProductData> =
        remoteDataSource.getInvestorProductsSafeCall(bearerToken)


    suspend fun addOneOffPayment(bearerToken : String?, amount : String, investorProductId : String) : Result<PaymentResponse> =
        remoteDataSource.addOneOffPaymentSafeCall(bearerToken, amount, investorProductId)
}