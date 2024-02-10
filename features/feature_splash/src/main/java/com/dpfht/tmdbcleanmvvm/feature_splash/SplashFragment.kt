package com.dpfht.tmdbcleanmvvm.feature_splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dpfht.tmdbcleanmvvm.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

  override val viewModel by viewModels<SplashViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewModel()
    viewModel.start()
  }

  override fun observeViewModel() {
    super.observeViewModel()

    viewModel.hasFinishedDelaying.observe(viewLifecycleOwner) { hasFinished ->
      if (hasFinished) {
        navigateToNextScreen()
      }
    }
  }

  private fun navigateToNextScreen() {
    navigationService.navigateToGender()
  }

  override fun onStart() {
    super.onStart()
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
  }

  override fun onStop() {
    super.onStop()
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
  }
}
