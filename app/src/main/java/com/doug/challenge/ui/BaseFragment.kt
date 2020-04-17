package com.doug.challenge.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.doug.challenge.ui.dialogs.ErrorDialog

/**
 * Base fragment that holds some common logic to display errors
 */
abstract class BaseFragment : Fragment() {

    private fun createErrorDialog(title: String?, message: String): ErrorDialog? {
        return activity?.let { activity ->
            if (isReadyToShowDialog(activity)) {
                ErrorDialog.newInstance(title, message)
            } else {
                null
            }
        }
    }

    protected fun showErrorDialog(title: String? = null, message: String) {
        val errorDialog = createErrorDialog(title, message)
        errorDialog?.show(childFragmentManager)
    }

    private fun isReadyToShowDialog(activity: FragmentActivity) =
        !activity.isFinishing && !activity.isChangingConfigurations

    protected fun hideActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    protected fun showActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}
