package com.doug.challenge.ui.reward

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doug.challenge.R
import com.doug.challenge.ui.BaseFragment

class RewardFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showActionBar()
        return inflater.inflate(R.layout.fragment_reward, container, false)
    }
}
