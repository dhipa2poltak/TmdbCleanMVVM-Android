package com.dpfht.tmdbcleanmvvm.feature_movie_reviews.di

import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewEntity
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MovieReviewsModule {

  @Provides
  fun provideReviews(): ArrayList<ReviewEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideGetMovieReviewUseCase(appRepository: AppRepository): GetMovieReviewUseCase {
    return GetMovieReviewUseCaseImpl(appRepository)
  }
}
