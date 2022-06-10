package com.dpfht.tmdbcleanmvvm.core.domain.model

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
