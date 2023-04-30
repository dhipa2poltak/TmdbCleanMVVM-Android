package com.dpfht.tmdbcleanmvvm.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dpfht.tmdbcleanmvvm.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment: Fragment() {

  private lateinit var binding: FragmentSplashBinding

  @Inject
  lateinit var navigationService: NavigationInterface

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      navigateToNextScreen()
    }, 3000)
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
