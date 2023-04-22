package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.ReviewDomain

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain>
}
