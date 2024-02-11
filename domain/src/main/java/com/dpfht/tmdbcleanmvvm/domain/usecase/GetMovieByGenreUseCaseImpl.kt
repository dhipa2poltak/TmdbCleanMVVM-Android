package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.entity.AppException
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.entity.Result

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain> {
    return try {
      Result.Success(appRepository.getMoviesByGenre(genreId.toString(), page))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
