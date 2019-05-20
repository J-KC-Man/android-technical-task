package com.example.minimoneybox.datasource

import android.util.Log
import com.example.minimoneybox.datasource.model.UserLogin
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

        val response = request.await() // wait for result
        if (response.isSuccessful) {
            //Do something with response e.g show to the UI.
            val jsonresponse = response.body()

           // Log.i("RemoteDatasource", jsonresponse.toString())



            return Result.Success(jsonresponse.toString())
        } else {
            return Result.Error(IOException("Error ${response.code()} occurred!"))
        }
    }
}