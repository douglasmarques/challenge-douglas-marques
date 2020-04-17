package com.doug.challenge.network.model

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "status")
    val status: String
)
