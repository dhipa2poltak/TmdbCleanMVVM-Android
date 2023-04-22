package com.dpfht.tmdbcleanmvvm.core.domain.entity

data class DiscoverMovieByGenreDomain(
  val page: Int = 0,
  val results: List<MovieEntity> = arrayListOf(),
  val totalPages: Int = 0,
  val totalResults: Int = 0
)
