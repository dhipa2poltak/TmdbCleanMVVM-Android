package com.dpfht.tmdbcleanmvvm.feature_movie_reviews.di

import android.content.Context
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.MovieReviewsFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MovieReviewsDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MovieReviewsDependency::class, NavigationDependency::class],
  modules = [MovieReviewsModule::class])
interface MovieReviewsComponent {

  fun inject(movieReviewsFragment: MovieReviewsFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun dependency(movieReviewsDependency: MovieReviewsDependency): Builder
    fun navDependency(navigationDependency: NavigationDependency): Builder
    fun build(): MovieReviewsComponent
  }
}