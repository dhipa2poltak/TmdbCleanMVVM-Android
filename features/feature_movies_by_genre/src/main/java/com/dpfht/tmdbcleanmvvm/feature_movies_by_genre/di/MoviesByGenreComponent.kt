package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.MoviesByGenreFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MoviesByGenreDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MoviesByGenreDependency::class, NavigationDependency::class],
  modules = [MoviesByGenreModule::class])
interface MoviesByGenreComponent {

  fun inject(moviesByGenreFragment: MoviesByGenreFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(moviesByGenreDependency: MoviesByGenreDependency): Builder
    fun navDependency(navigationDependency: NavigationDependency): Builder
    fun build(): MoviesByGenreComponent
  }
}
