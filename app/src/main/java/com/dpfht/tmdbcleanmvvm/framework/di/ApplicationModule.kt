package com.dpfht.tmdbcleanmvvm.framework.di

import com.dpfht.tmdbcleanmvvm.core.data.repository.AppDataSource
import com.dpfht.tmdbcleanmvvm.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideAppRepository(remoteDataSource: AppDataSource): AppRepository {
    return AppRepositoryImpl(remoteDataSource)
  }
}
