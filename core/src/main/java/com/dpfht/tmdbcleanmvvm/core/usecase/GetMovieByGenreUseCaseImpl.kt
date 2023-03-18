package com.dpfht.tmdbcleanmvvm.core.usecase

import com.dpfht.tmdbcleanmvvm.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieByGenreResult

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
