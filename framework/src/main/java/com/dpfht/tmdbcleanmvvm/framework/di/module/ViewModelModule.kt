package com.dpfht.tmdbcleanmvvm.framework.di.module

import com.dpfht.tmdbcleanmvvm.domain.model.Genre
import com.dpfht.tmdbcleanmvvm.domain.model.Movie
import com.dpfht.tmdbcleanmvvm.domain.model.Review
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCaseImpl
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieTrailerUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

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

  @Provides
  fun provideGetMovieTrailerUseCase(appRepository: AppRepository): GetMovieTrailerUseCase {
    return GetMovieTrailerUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGenres(): ArrayList<Genre> {
    return arrayListOf()
  }

  @Provides
  fun provideMovies(): ArrayList<Movie> {
    return arrayListOf()
  }

  @Provides
  fun provideReviews(): ArrayList<Review> {
    return arrayListOf()
  }
}
