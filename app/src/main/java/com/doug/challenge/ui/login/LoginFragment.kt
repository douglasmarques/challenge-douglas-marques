package com.doug.challenge.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doug.challenge.R
import com.doug.challenge.ui.BaseFragment

class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideActionBar()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}
