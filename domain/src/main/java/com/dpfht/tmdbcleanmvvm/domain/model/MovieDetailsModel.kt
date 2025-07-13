package com.dpfht.tmdbcleanmvvm.domain.model

data class MovieDetailsModel(
  val id: Int = -1,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = "",
  val genres: List<Genre>
)
