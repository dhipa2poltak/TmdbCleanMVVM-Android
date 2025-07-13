package com.dpfht.tmdbcleanmvvm.domain.model

data class ReviewModel(
  val results: List<Review> = listOf(),
  val page: Int = -1,
)
