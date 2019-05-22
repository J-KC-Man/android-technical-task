package com.example.minimoneybox.RemoteDataSourceTest

import com.example.minimoneybox.datasource.ApiService
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.Result
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.IOException

class RemoteDataSourceTest {

    private lateinit var apiService: ApiService
    private lateinit var _sut : RemoteDataSource


    @Before
    fun setUp() {

        // arrange
        apiService = ApiServiceGenerator.createService()
        _sut = RemoteDataSource(apiService)
    }


    @Test
    fun login() {

        // act
        val result = runBlocking { _sut.login(email = "androidtest@moneyboxapp.com", password =  "P455word12") }
        // assert
        assertThat(result, instanceOf(Result.Success::class.java))
    }

    @Test
    fun login_failure() {

        val expected = Result.Error(IOException("Incorrect email address or password. Please check and try again."))
        // act
        val result = runBlocking { _sut.login(email = "androidtest@moneyboxapp.com", password =  "P455word12e") }

        // assert
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun getInvestorProducts_Success() {

        val token = "JcC+Rg8G/0i4GmPup9T3TPY7EsHkr2i4/4qiqBN/OLI="
        val expected =""
        val result = runBlocking { _sut.getInvestorProducts(bearerToken = token) }

        // assert
        // assertEquals() can be executed synchronously after the code inside of the runBlocking() method.
        assertEquals(expected, result.toString())
    }
}