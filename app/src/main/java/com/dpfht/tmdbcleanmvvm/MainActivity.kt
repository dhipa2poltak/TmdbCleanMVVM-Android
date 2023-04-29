package com.dpfht.tmdbcleanmvvm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dpfht.tmdbcleanmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.demo_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  override fun onResume() {
    super.onResume()

    registerReceiver(receiverToGenre, IntentFilter("enter_genre"))
  }

  override fun onPause() {
    super.onPause()

    unregisterReceiver(receiverToGenre)
  }

  private val receiverToGenre = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
      navGraph.setStartDestination(R.id.genreFragment)

      navController.graph = navGraph
    }
  }
}
