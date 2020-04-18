package com.doug.challenge.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.doug.challenge.R
import com.doug.challenge.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val repository: LoginRepository
) : ViewModel() {

    val loadingObserver = MutableLiveData<Boolean>()
    val errorObserver = MutableLiveData(0)
    val navigationObserver = MutableLiveData<NavDirections?>()

    fun login(password: String) = viewModelScope.launch {
        // set loading state to true, it means the screen will display the loading widget
        loadingObserver.value = true
        if (isValidPassword(password)) {
            try {
                //  call the repository to login
                if (repository.login(password)) {
                    navigateToReward()
                } else {
                    showIncorrectOtpError()
                }
            } catch (exception: Exception) {
                showGenericError()
            }
        } else {
            showInvalidOtpSizeError()
        }

    }

    private fun isValidPassword(password: String): Boolean = password.length == 4

    /**
     * Hide the loading spinner and navigate to reward screen.
     */
    private fun navigateToReward() {
        loadingObserver.value = false
        navigationObserver.value =
            LoginFragmentDirections.actionLoginFragmentToRewardFragment()
    }

    /**
     * Hide the loading spinner and show the incorrect OTP error.
     */
    private fun showIncorrectOtpError() {
        loadingObserver.value = false
        errorObserver.value = R.string.incorrect_otp_error
    }

    /**
     * Hide the loading spinner and show the generic error.
     */
    private fun showGenericError() {
        loadingObserver.value = false
        errorObserver.value = R.string.dialog_error_generic
    }

    /**
     * Hide the loading spinner and show the invalid OTP size error.
     */
    private fun showInvalidOtpSizeError() {
        loadingObserver.value = false
        errorObserver.value = R.string.invalid_size_otp_error
    }
}
