package com.doug.challenge.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        setUpSubmitButton()
        setUpPasswordOtpView()
        observeViewModel()
    }

    private fun setUpSubmitButton() {
        submitButton.setOnClickListener {
            viewModel.login(passwordOtpView.text.toString())
        }
    }

    private fun setUpPasswordOtpView() {
        passwordOtpView.setOnEditorActionListener(OnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.login(view.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        submitButton.setOnClickListener {
            viewModel.login(passwordOtpView.text.toString())
        }
    }

    private fun observeViewModel() {
        observeLoading()
        observeErrors()
        observeNavigation()
    }

    /**
     * observe if the view model emits changes to the loading state
     */
    private fun observeLoading() {
        viewModel.loadingObserver.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                submitButton.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                submitButton.visibility = View.VISIBLE
            }
        })
    }

    /**
     * observe if the view model pushes any error messages
     */
    private fun observeErrors() {
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer { errorResource ->
            // show the error message
            if (errorResource > 0) {
                showErrorDialog(message = getString(errorResource))
                viewModel.errorObserver.value = 0
            }
        })
    }

    /**
     * observe if the view model requires any navigation
     */
    private fun observeNavigation() {
        viewModel.navigationObserver.observe(viewLifecycleOwner, Observer { directions ->
            directions?.let {
                findNavController().navigate(directions)
                viewModel.navigationObserver.value = null
            }
        })
    }
}
