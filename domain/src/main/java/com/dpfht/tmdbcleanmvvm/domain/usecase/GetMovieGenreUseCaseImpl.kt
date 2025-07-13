package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.model.AppException
import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.model.Result

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): Result<GenreModel> {
    return try {
      Result.Success(appRepository.getMovieGenre())
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
