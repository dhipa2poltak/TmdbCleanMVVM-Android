package com.dpfht.tmdbcleanmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dpfht.tmdbcleanmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.dpfht.tmdbcleanmvvm.framework.R as frameworkR
import com.dpfht.tmdbcleanmvvm.navigation.R as navigationR

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val appBarConfiguration = AppBarConfiguration(
      setOf(navigationR.id.genreFragment)
    )

    val navHostFragment =
      supportFragmentManager.findFragmentById(frameworkR.id.demo_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}
