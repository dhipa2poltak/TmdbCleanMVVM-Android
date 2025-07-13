package com.dpfht.tmdbcleanmvvm.domain.model

data class TrailerModel(
  val id: Int = -1,
  val results: List<Trailer> = listOf()
)
