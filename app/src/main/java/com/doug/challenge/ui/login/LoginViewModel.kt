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
        try {
            // set loading state to true, it means the screen will display the loading widget
            loadingObserver.value = true
            //  call the repository to login
            val loginSuccessful = repository.login(password)
            // if login is successful navigate to reward screen
            if (loginSuccessful) {
                navigationObserver.value =
                    LoginFragmentDirections.actionLoginFragmentToRewardFragment()
            } else {
                errorObserver.value = R.string.invalid_otp_error
            }
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
        } catch (exception: Exception) {
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
            // set a generic error message if there is any error to retrieve properties
            errorObserver.value = R.string.dialog_error_generic
        }
    }
}
