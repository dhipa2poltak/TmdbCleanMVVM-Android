package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerDomain

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain>
}
