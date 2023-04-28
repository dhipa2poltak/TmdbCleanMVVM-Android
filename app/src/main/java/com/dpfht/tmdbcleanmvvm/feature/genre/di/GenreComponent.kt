package com.dpfht.tmdbcleanmvvm.feature.genre.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature.genre.GenreFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.GenreDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [GenreDependency::class],
           modules = [GenreModule::class])
interface GenreComponent {

  fun inject(genreFragment: GenreFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(genreDependency: GenreDependency): Builder
    fun build(): GenreComponent
  }
}
