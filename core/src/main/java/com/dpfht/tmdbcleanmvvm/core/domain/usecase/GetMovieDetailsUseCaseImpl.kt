package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain> {
    return appRepository.getMovieDetail(movieId)
  }
}
