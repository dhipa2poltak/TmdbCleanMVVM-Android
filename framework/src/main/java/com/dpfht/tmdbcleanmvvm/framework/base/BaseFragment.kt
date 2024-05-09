package com.dpfht.tmdbcleanmvvm.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dpfht.tmdbcleanmvvm.framework.databinding.LayoutLoadingBinding
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationService
import javax.inject.Inject


abstract class BaseFragment<VDB: ViewDataBinding, VM: BaseViewModel>(
  @LayoutRes protected val contentLayoutId: Int
): Fragment() {

  protected lateinit var binding: VDB
  abstract val viewModel: VM

  @Inject
  protected lateinit var navigationService: NavigationService

  private lateinit var loadingView: View

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)

    val tmpLoadingView = getCustomLoadingView()
    if (tmpLoadingView != null) {
      loadingView = tmpLoadingView
    } else {
      val loadingBinding = LayoutLoadingBinding.inflate(inflater, container, false)
      loadingView = loadingBinding.root
      loadingView.visibility = View.GONE
      (binding.root as ViewGroup).addView(loadingView)
    }

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupView(view, savedInstanceState)
    defaultObserveViewModel()
    observeViewModel()
    startViewModel()
  }

  open fun setupView(view: View, savedInstanceState: Bundle?) {}

  private fun defaultObserveViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { isLoading ->
      loadingView.visibility = if (isLoading) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }

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

  open fun observeViewModel() {}

  open fun startViewModel() {}

  private fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(com.dpfht.tmdbcleanmvvm.framework.R.string.canceled_message))
  }

  open fun getCustomLoadingView(): View? {
    return null
  }
}
