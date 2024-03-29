package com.dpfht.tmdbcleanmvvm.domain.entity

data class MovieDetailsDomain(
  val id: Int = -1,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = "",
  val genres: List<GenreEntity>
)
