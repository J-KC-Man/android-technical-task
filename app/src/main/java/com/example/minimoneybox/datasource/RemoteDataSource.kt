package com.example.minimoneybox.datasource

import com.example.minimoneybox.datasource.model.LoginFailure
import com.example.minimoneybox.datasource.model.UserLogin
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
        errorMessage = "Unable to login"
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
            val errorResponse: LoginFailure? = gson.fromJson(
                response.errorBody()?.charStream(), // processe response as a stream
                LoginFailure::class.java)

            return Result.Error(IOException("${errorResponse?.Message}"))
        }
    }
}