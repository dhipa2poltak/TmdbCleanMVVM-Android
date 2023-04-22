package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain>
}
