package com.example.minimoneybox.RemoteDataSourceTest

import com.example.minimoneybox.datasource.ApiService
import com.example.minimoneybox.datasource.ApiServiceGenerator
import com.example.minimoneybox.datasource.RemoteDataSource
import com.example.minimoneybox.datasource.Result
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

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



}