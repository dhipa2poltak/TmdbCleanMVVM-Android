package com.dpfht.tmdbcleanmvvm.core.domain.model

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Review

data class GetMovieReviewResult(
  val reviews: List<Review>,
  val page: Int
)
