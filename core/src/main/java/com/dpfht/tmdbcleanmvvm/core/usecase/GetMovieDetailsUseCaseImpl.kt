package com.dpfht.tmdbcleanmvvm.core.usecase

import com.dpfht.tmdbcleanmvvm.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieDetailsResult

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
