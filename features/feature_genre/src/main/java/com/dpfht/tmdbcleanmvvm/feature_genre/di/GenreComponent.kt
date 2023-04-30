package com.dpfht.tmdbcleanmvvm.feature_genre.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature_genre.GenreFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.GenreDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [GenreDependency::class, NavigationDependency::class],
           modules = [GenreModule::class])
interface GenreComponent {

  fun inject(genreFragment: GenreFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(genreDependency: GenreDependency): Builder
    fun navDependency(navigationDependency: NavigationDependency): Builder
    fun build(): GenreComponent
  }
}
