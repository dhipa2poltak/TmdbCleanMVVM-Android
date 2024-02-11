package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.entity.AppException
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain> {
    return try {
      Result.Success(appRepository.getMovieTrailer(movieId))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
