package com.example.minimoneybox.datasource.model

import com.google.gson.annotations.SerializedName

data class UserLoginServerResponse(
    @SerializedName("Session") val Session : LoginSession
)