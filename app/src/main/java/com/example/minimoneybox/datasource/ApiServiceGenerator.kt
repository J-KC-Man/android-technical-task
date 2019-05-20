package com.example.minimoneybox.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceGenerator {

    const val BASE_URL = "https://api-test01.moneyboxapp.com/"

    fun createService(): ApiService {

        // create retrofit object
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL).addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .build()

        return builder.create<ApiService>(ApiService::class.java)
    }

    private fun provideOkHttpClient(logging: HttpLoggingInterceptor) : OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        // add logging as last interceptor
        httpClient.addInterceptor(logging)  // <-- this is the important line!

        return httpClient.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}