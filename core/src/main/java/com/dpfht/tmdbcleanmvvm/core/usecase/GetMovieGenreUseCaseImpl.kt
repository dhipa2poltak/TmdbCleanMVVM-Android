package com.dpfht.tmdbcleanmvvm.core.usecase

import com.dpfht.tmdbcleanmvvm.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
