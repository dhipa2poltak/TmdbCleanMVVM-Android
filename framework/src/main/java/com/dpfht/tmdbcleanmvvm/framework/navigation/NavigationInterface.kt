package com.dpfht.tmdbcleanmvvm.framework.navigation

interface NavigationInterface {

  fun navigateToGender()
  fun navigateToMoviesByGender(genreId: Int, genreName: String)
  fun navigateToMovieDetails(movieId: Int)
  fun navigateToMovieReviews(movieId: Int, movieTitle: String)
  fun navigateToMovieTrailer(movieId: Int)
  fun navigateToErrorMessage(message: String)
}
