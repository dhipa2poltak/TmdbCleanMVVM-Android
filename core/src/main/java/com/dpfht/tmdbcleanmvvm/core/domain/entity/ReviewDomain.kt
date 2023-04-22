package com.dpfht.tmdbcleanmvvm.core.domain.entity

data class ReviewDomain(
  val results: List<ReviewEntity> = arrayListOf(),
  val page: Int = -1,
)
