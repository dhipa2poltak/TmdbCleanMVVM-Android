package com.dpfht.tmdbcleanmvvm.core.domain.model

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Movie

data class GetMovieByGenreResult(
  val movies: List<Movie>,
  val page: Int
)
