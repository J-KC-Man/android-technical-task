package com.example.minimoneybox.datasource

import com.example.minimoneybox.datasource.model.UserLogin
import com.example.minimoneybox.datasource.model.UserLoginServerResponse
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /*
    * Methods declared on an interface represent a single remote API endpoint.
    * Annotations describe how the method maps to an HTTP request.
    * */
    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("users/login")
    fun login(@Body userLogin: UserLogin) : Deferred<Response<UserLoginServerResponse>>

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @GET("investorproducts")
    fun getUserAccounts(@Header("Authorization") token : String)
            : Deferred<Response<AllInvestorProductData>>
}