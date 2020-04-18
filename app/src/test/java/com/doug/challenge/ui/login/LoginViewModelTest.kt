package com.doug.challenge.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doug.challenge.R
import com.doug.challenge.repository.LoginRepository
import com.doug.challenge.test.CoroutinesTestRule
import com.doug.challenge.test.testObserver
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @JvmField
    @Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @JvmField
    @Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var repository: LoginRepository

    lateinit var viewModel: LoginViewModel

    @Test
    fun `login - when password is empty should display invalid size error`() {
        val password = ""
        repository = mock()
        viewModel = LoginViewModel(repository)

        viewModel.loadingObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.navigationObserver.testObserver()

        viewModel.login(password)
        verifyZeroInteractions(repository)

        assertEquals(viewModel.errorObserver.value, R.string.invalid_size_otp_error)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(viewModel.navigationObserver.value, null)
    }


    @Test
    fun `login - when the password is right should navigate to reward`() {
        val password = "1234"
        repository = mock {
            onBlocking { login(password) } doReturn true
        }
        viewModel = LoginViewModel(repository)

        viewModel.loadingObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.navigationObserver.testObserver()

        viewModel.login(password)
        verifyBlocking(repository, { repository.login(password) })

        assertEquals(viewModel.errorObserver.value, 0)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(
            viewModel.navigationObserver.value,
            LoginFragmentDirections.actionLoginFragmentToRewardFragment()
        )
    }

    @Test
    fun `login - when the password is incorrect should display incorrect otp error`() {
        val password = "1235"
        repository = mock {
            onBlocking { login(password) } doReturn false
        }
        viewModel = LoginViewModel(repository)

        viewModel.loadingObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.navigationObserver.testObserver()

        viewModel.login(password)
        verifyBlocking(repository, { repository.login(password) })

        assertEquals(viewModel.errorObserver.value, R.string.incorrect_otp_error)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(viewModel.navigationObserver.value, null)
    }

    @Test
    fun `login - when some network error happens should display generic error`() {
        val password = "1234"

        repository = mock {
            onBlocking { login(password) } doThrow mock<HttpException>()
        }
        viewModel = LoginViewModel(repository)

        viewModel.loadingObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.navigationObserver.testObserver()

        viewModel.login(password)
        verifyBlocking(repository, { repository.login(password) })

        assertEquals(viewModel.errorObserver.value, R.string.dialog_error_generic)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(viewModel.navigationObserver.value, null)
    }
}
