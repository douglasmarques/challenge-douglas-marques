package com.doug.challenge.network

import com.doug.challenge.network.model.LoginRequest
import com.doug.challenge.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    companion object {
        const val URL = "http://floral-cherry-7673.getsandbox.com/"
    }

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}

