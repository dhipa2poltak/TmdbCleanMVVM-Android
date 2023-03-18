package com.dpfht.tmdbcleanmvvm.framework.di

import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieReviewUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

  @Provides
  fun provideGetMovieGenreUseCase(appRepository: AppRepository): GetMovieGenreUseCase {
    return GetMovieGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieByGenreUseCase(appRepository: AppRepository): GetMovieByGenreUseCase {
    return GetMovieByGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieDetailsUseCase(appRepository: AppRepository): GetMovieDetailsUseCase {
    return GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieReviewUseCase(appRepository: AppRepository): GetMovieReviewUseCase {
    return GetMovieReviewUseCaseImpl(appRepository)
  }
}
