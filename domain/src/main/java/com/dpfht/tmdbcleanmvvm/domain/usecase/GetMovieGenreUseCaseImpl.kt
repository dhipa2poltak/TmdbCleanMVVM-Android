package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.entity.AppException
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.entity.Result

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): Result<GenreDomain> {
    return try {
      Result.Success(appRepository.getMovieGenre())
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
