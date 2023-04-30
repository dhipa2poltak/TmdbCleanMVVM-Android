package com.dpfht.tmdbcleanmvvm.framework.di.dependency

import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface NavigationDependency {

  fun getNavigationService(): NavigationInterface
}
