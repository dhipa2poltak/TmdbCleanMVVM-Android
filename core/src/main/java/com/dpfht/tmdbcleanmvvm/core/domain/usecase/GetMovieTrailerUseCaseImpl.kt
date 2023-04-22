package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerDomain

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain> {
    return appRepository.getMovieTrailer(movieId)
  }
}
