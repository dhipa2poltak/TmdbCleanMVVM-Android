package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.entity.AppException
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain> {
    return try {
      Result.Success(appRepository.getMovieReviews(movieId, page))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
