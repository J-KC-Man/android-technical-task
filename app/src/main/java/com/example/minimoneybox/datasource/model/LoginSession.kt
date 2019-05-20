package com.example.minimoneybox.datasource.model

data class LoginSession(
    val BearerToken : String,
    val ExternalSessionId : String,
    val SessionExternalId : String,
    val ExpiryInSeconds : Int

)