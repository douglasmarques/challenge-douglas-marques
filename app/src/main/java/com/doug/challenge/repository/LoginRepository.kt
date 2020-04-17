package com.doug.challenge.repository

import com.doug.challenge.network.Api
import com.doug.challenge.network.model.LoginRequest
import retrofit2.HttpException
import java.util.*

class LoginRepository(
    private val api: Api
) {
    /**
     * Prepare the request and call the API to login. If login is valid
     * returns true else check if HTTP code is 401 and returns false, otherwise
     * just rethrow the exception
     */
    suspend fun login(password: String): Boolean {
        val loginRequest = LoginRequest(password)
        return try {
            val response = api.login(loginRequest)
            response.status.toLowerCase(Locale.ROOT) == "ok"
        } catch (httpEx: HttpException) {
            if (httpEx.code() == 401) {
                false
            } else {
                throw httpEx
            }
        }
    }
}
