package com.dpfht.tmdbcleanmvvm.framework.di

import com.dpfht.tmdbcleanmvvm.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CollectionModule {

  @Provides
  fun provideGenres(): ArrayList<GenreEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideMovies(): ArrayList<MovieEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideReviews(): ArrayList<ReviewEntity> {
    return arrayListOf()
  }
}
