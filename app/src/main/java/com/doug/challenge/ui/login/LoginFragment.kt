package com.doug.challenge.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.doug.challenge.R
import com.doug.challenge.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideActionBar()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpButton()
        observeViewModel()
    }

    private fun setUpButton() {
        submitButton.setOnClickListener {
            viewModel.login(passwordTextView.text.toString())
        }
    }

    private fun observeViewModel() {
        observeLoadingState()
        observerErrorState()
    }

    /**
     * observe if the view model emits changes to the loading state
     */
    private fun observeLoadingState() {
        viewModel.loadingObserver.observe(viewLifecycleOwner, Observer { isLoading ->
            // show the refreshing widget
//            if (isLoading) {
//                if (!swipeRefreshLayout.isRefreshing) {
//                    swipeRefreshLayout.isRefreshing = true
//                }
//            } else {
//                // hide the refreshing widget
//                swipeRefreshLayout.isRefreshing = false
//            }
        })
    }

    /**
     * observe if the view model pushes any error messages
     */
    private fun observerErrorState() {
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer { errorResource ->
            // show the error message
            if (errorResource > 0) {
                showErrorDialog(message = getString(errorResource))
                viewModel.errorObserver.value = 0
            }
        })
    }
}
