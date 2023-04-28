package com.dpfht.tmdbcleanmvvm.framework.di.dependency

import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MovieDetailsDependency {

  fun getAppRepository(): AppRepository
}
