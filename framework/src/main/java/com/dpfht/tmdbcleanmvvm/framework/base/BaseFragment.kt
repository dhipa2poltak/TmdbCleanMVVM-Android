package com.dpfht.tmdbcleanmvvm.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationService
import javax.inject.Inject

abstract class BaseFragment<VDB: ViewDataBinding, VM: BaseViewModel>(
  @LayoutRes protected val contentLayoutId: Int
): Fragment() {

  protected lateinit var binding: VDB
  abstract val viewModel: VM

  @Inject
  protected lateinit var navigationService: NavigationService

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)

    return binding.root
  }

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

