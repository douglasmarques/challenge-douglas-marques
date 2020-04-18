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
                    showInvalidOtpError()
                }
            } catch (exception: Exception) {
                showGenericError()
            }
        } else {
            showInvalidOtpSizeError()
        }

    }

    private fun isValidPassword(password: String): Boolean = password.length == 4

    private fun navigateToReward() {
        // set loading state to false it means the UI will hide the loading widget
        loadingObserver.value = false
        // navigate to reward screen
        navigationObserver.value =
            LoginFragmentDirections.actionLoginFragmentToRewardFragment()
    }

    private fun showInvalidOtpError() {
        // set loading state to false it means the UI will hide the loading widget
        loadingObserver.value = false
        // show the invalid otp error
        errorObserver.value = R.string.invalid_otp_error
    }

    private fun showGenericError() {
        // set loading state to false it means the UI will hide the loading widget
        loadingObserver.value = false
        // set a generic error message if there is some error to login
        errorObserver.value = R.string.dialog_error_generic
    }

    private fun showInvalidOtpSizeError() {
        // set loading state to false it means the UI will hide the loading widget
        loadingObserver.value = false
        // show the invalid otp size error
        errorObserver.value = R.string.invalid_size_otp_error
    }
}
