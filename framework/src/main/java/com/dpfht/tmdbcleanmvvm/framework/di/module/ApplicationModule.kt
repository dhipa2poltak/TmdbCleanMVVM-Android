package com.dpfht.tmdbcleanmvvm.framework.di.module

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.data.repository.AppRepositoryImpl
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
