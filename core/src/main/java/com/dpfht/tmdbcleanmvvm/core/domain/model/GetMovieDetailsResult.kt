package com.dpfht.tmdbcleanmvvm.core.domain.model

data class GetMovieDetailsResult(
  val movieId: Int,
  val title: String,
  val overview: String,
  val posterPath: String
)
