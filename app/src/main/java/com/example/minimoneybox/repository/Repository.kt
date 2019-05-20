package com.example.minimoneybox.repository

import com.example.minimoneybox.datasource.RemoteDataSource

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun login(email : String, password: String) {
        remoteDataSource.loginUser(email, password)
    }
}