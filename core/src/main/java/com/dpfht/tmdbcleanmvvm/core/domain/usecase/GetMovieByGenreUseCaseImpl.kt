package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
