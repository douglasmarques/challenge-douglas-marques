package com.doug.challenge.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doug.challenge.R
import com.doug.challenge.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val repository: LoginRepository
) : ViewModel() {

    val loadingObserver = MutableLiveData<Boolean>()
    val errorObserver = MutableLiveData(0)

    fun login(password: String) = viewModelScope.launch {
        try {
            // set loading state to true, it means the screen will display the loading widget
            loadingObserver.value = true
            //  call the repository to login
            val loginSuccessful = repository.login(password)
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
            // if login is successful navigate to next screen
            if (loginSuccessful) {
                errorObserver.value = R.string.app_name
            } else {
                errorObserver.value = R.string.invalid_otp_error
            }
        } catch (exception: Exception) {
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
            // set a generic error message if there is any error to retrieve properties
            errorObserver.value = R.string.dialog_error_generic
        }
    }
}
