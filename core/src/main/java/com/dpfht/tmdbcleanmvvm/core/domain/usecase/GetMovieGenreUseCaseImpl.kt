package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): Result<GenreDomain> {
    return appRepository.getMovieGenre()
  }
}
