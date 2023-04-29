package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.MoviesByGenreFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MoviesByGenreDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MoviesByGenreDependency::class],
  modules = [MoviesByGenreModule::class])
interface MoviesByGenreComponent {

  fun inject(moviesByGenreFragment: MoviesByGenreFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(moviesByGenreDependency: MoviesByGenreDependency): Builder
    fun build(): MoviesByGenreComponent
  }
}
