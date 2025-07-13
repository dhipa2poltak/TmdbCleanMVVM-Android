package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.model.AppException
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvvm.domain.model.Result

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsModel> {
    return try {
      Result.Success(appRepository.getMovieDetail(movieId))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
