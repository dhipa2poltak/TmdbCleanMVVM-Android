package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.di

import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MoviesByGenreModule {

  @Provides
  fun provideMovies(): ArrayList<MovieEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideGetMovieByGenreUseCase(appRepository: AppRepository): GetMovieByGenreUseCase {
    return GetMovieByGenreUseCaseImpl(appRepository)
  }
}
