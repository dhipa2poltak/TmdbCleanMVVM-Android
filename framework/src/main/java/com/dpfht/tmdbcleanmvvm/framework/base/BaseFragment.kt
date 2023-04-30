package com.dpfht.tmdbcleanmvvm.framework.base

import androidx.fragment.app.Fragment
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface

abstract class BaseFragment<T: BaseViewModel>: Fragment() {

  abstract val viewModel: T
  abstract var navigationService: NavigationInterface

  open fun observeViewModel() {
    viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
      if (message.isNotEmpty()) {
        showErrorMessage(message)
      }
    }

    viewModel.showCanceledMessage.observe(viewLifecycleOwner) { isShow ->
      if (isShow) {
        showCanceledMessage()
      }
    }
  }

  private fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(com.dpfht.tmdbcleanmvvm.framework.R.string.canceled_message))
  }
}
