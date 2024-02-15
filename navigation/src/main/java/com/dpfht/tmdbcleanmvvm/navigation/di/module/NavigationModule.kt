package com.dpfht.tmdbcleanmvvm.navigation.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationService
import com.dpfht.tmdbcleanmvvm.framework.R as frameworkR
import com.dpfht.tmdbcleanmvvm.navigation.NavigationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

  @Provides
  fun provideNavController(@ActivityContext context: Context): NavController {
    val navHostFragment =  (context as AppCompatActivity).supportFragmentManager.findFragmentById(frameworkR.id.demo_nav_host_fragment) as NavHostFragment

    return navHostFragment.navController
  }

  @Provides
  fun provideNavigationService(@ActivityContext context: Context, navController: NavController): NavigationService {
    return NavigationServiceImpl(navController, context as AppCompatActivity)
  }
}
