package com.example.minimoneybox.datasource.model

data class LoginFailure (
    val Name : String,
    val Message : String,
    val ValidationErrors : Array<String>
)