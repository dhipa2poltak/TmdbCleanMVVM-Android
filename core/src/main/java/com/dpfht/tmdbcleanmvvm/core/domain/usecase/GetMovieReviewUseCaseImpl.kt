package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.ReviewDomain

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
