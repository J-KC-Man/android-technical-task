package com.example.minimoneybox.datasource.model

data class FailedRequest (
    val Name : String,
    val Message : String,
    val ValidationErrors : Array<String>
)