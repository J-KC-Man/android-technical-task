package com.example.minimoneybox.repository

import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.Result
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun login(email : String, password: String) : Result<String> {
        return remoteDataSource.loginUser(email, password)
    }

    suspend fun getInvestorProducts(bearerToken : String?) : Result<AllInvestorProductData> {
        return remoteDataSource.getInvestorProductsSafeCall(bearerToken)
    }
}