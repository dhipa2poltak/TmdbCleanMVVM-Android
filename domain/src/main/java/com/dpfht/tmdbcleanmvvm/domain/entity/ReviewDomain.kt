package com.dpfht.tmdbcleanmvvm.domain.entity

data class ReviewDomain(
  val results: List<ReviewEntity> = listOf(),
  val page: Int = -1,
)
