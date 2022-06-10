package com.dpfht.tmdbcleanmvvm.core.domain.model

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Genre

data class GetMovieGenreResult(
  val genres: List<Genre>
)
