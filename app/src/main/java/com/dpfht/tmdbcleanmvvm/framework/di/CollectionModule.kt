package com.dpfht.tmdbcleanmvvm.framework.di

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Movie
import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Review
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CollectionModule {

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
