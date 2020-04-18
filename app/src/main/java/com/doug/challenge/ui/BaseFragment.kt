package com.doug.challenge.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.doug.challenge.ui.dialogs.ErrorDialog

/**
 * Base fragment that holds some common logic to display errors and hide the Keyboard
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

    protected fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputManager?.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}
