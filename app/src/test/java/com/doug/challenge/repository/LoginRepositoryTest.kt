package com.doug.challenge.repository

import com.doug.challenge.network.Api
import com.doug.challenge.network.model.LoginRequest
import com.doug.challenge.network.model.LoginResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {

    lateinit var mockApi: Api
    lateinit var repository: LoginRepository

    @Test
    fun `login - API returns success login, repository should return true`() {
        val password = "1234"
        val loginRequest = LoginRequest(password)
        mockApi = mock {
            onBlocking { login(loginRequest) } doReturn LoginResponse(
                status = "ok"
            )
        }
        repository = LoginRepository(mockApi)
        runBlocking {
            val successfulLogin = repository.login(password)
            assertEquals(true, successfulLogin)
            verify(mockApi).login(loginRequest)
        }
    }

    @Test
    fun `login - API returns 401 Unauthorised, repository should return false`() {
        val password = "1235"
        val loginRequest = LoginRequest(password)
        val httpException = mock<HttpException> {
            on { code() } doReturn 401
        }
        mockApi = mock {
            onBlocking { login(loginRequest) } doThrow httpException
        }
        repository = LoginRepository(mockApi)
        runBlocking {
            val successfulLogin = repository.login(password)
            assertEquals(false, successfulLogin)
            verify(mockApi).login(loginRequest)
        }
    }

    @Test(expected = HttpException::class)
    fun `login - API returns 500, repository should throw exception`() {
        val password = "1234"
        val loginRequest = LoginRequest(password)
        val httpException = mock<HttpException> {
            on { code() } doReturn 500
        }
        mockApi = mock {
            onBlocking { login(loginRequest) } doThrow httpException
        }
        repository = LoginRepository(mockApi)
        runBlocking {
            repository.login(password)
            verify(mockApi).login(loginRequest)
        }
    }
}
