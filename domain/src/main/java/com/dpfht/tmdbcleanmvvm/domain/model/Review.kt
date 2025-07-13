package com.dpfht.tmdbcleanmvvm.domain.model

data class Review(
  val author: String = "",
  val authorDetails: AuthorDetails? = null,
  val content: String = "",
)
