package com.example.minimoneybox.datasource

import com.example.minimoneybox.datasource.model.FailedRequest
import com.example.minimoneybox.datasource.model.UserLogin
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import com.example.minimoneybox.datasource.model.quickPayment.QuickPaymentRequest
import com.example.minimoneybox.datasource.model.quickPayment.QuickPaymentResponse
import com.google.gson.Gson
import java.io.IOException

/*
* Top level function to replace static utility classes
* available from anywhere in the package
*
* call the lambda, in case of error, return error based on message passed in as a param
* */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> Result<T>, // suspending lambda to take a apicall suspend func as data
    errorMessage: String
): Result<T>
        = try {
    call.invoke()
} catch (e: Exception) { // catches any exception if api call fails
    Result.Error(IOException(errorMessage, e))
}

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun loginUser(
        email : String,
        password : String) = safeApiCall(
        call = { login(email, password) },
        errorMessage = "Unable to login, please check internet connection"
    )

    suspend fun login(email: String, password: String): Result<String> {

        val userLogin = UserLogin(email, password, Idfa = "ANYTHING")
        val request = apiService.login(userLogin)

        val response = request.await()
        if (response.isSuccessful) {

            val serverResponse = response.body()
            val bearerToken = serverResponse?.Session?.BearerToken

            return Result.Success(bearerToken)
        } else {
            val gson = Gson()

            // Deserialise Json
            val errorResponse: FailedRequest? = gson.fromJson(
                response.errorBody()?.charStream(), // process response as a stream
                FailedRequest::class.java)

            return Result.Error(IOException("${errorResponse?.Message}"))
        }
    }

    suspend fun getInvestorProductsSafeCall(
        bearerToken : String?) = safeApiCall(
        call = { getInvestorProducts(bearerToken) },
        errorMessage = "Unable to retrieve data, please login again"
    )

    suspend fun getInvestorProducts(bearerToken : String?) : Result<AllInvestorProductData>{
        val request = apiService.getUserAccounts("Bearer $bearerToken")

        val response = request.await()
        if (response.isSuccessful) {

            val serverResponse = response.body()

            return Result.Success(serverResponse)
        } else {
            val gson = Gson()

            // Deserialise Json
            val errorResponse: FailedRequest? = gson.fromJson(
                response.errorBody()?.charStream(), // process response as a stream
                FailedRequest::class.java)

            return Result.Error(IOException("${errorResponse?.Message}"))
        }
    }

    suspend fun addOneOffPaymentSafeCall(
        bearerToken : String?,
        amount : String,
        investorProductId : String?) = safeApiCall(
        call = { addOneOffPayment(bearerToken, amount, investorProductId) },
        errorMessage = "Unable send payment, please login again"
    )

    suspend fun addOneOffPayment(bearerToken : String?,
                                 amount : String,
                                 investorProductId : String?
    ) : Result<QuickPaymentResponse>{

        val paymentRequest = QuickPaymentRequest(amount, investorProductId)
        val request = apiService.addOneOffPayment("Bearer $bearerToken", paymentRequest)

        val response = request.await()
        if (response.isSuccessful) {

            val serverResponse = response.body()

            return Result.Success(serverResponse)
        } else {
            val gson = Gson()

            // Deserialise Json
            val errorResponse: FailedRequest? = gson.fromJson(
                response.errorBody()?.charStream(), // process response as a stream
                FailedRequest::class.java)

            return Result.Error(IOException("${errorResponse?.Message}"))
        }
    }
}