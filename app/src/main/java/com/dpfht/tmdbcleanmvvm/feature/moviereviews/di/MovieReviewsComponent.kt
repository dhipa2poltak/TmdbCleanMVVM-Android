package com.dpfht.tmdbcleanmvvm.feature.moviereviews.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature.moviereviews.MovieReviewsFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MovieReviewsDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MovieReviewsDependency::class],
  modules = [MovieReviewsModule::class])
interface MovieReviewsComponent {

  fun inject(movieReviewsFragment: MovieReviewsFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(movieReviewsDependency: MovieReviewsDependency): Builder
    fun build(): MovieReviewsComponent
  }
}
