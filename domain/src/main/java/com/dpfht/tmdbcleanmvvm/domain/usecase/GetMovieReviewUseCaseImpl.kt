package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.model.AppException
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.model.Result
import com.dpfht.tmdbcleanmvvm.domain.model.ReviewModel

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewModel> {
    return try {
      Result.Success(appRepository.getMovieReviews(movieId, page))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
