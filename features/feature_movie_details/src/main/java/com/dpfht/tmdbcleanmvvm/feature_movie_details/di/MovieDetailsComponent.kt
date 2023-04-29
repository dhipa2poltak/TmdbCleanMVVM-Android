package com.dpfht.tmdbcleanmvvm.feature_movie_details.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature_movie_details.MovieDetailsFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MovieDetailsDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MovieDetailsDependency::class],
  modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {

  fun inject(movieDetailsFragment: MovieDetailsFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(movieDetailsDependency: MovieDetailsDependency): Builder
    fun build(): MovieDetailsComponent
  }
}
