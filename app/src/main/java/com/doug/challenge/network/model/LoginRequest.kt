package com.doug.challenge.network.model

import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name = "code")
    val code: String
)
