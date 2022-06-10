package com.dpfht.tmdbcleanmvvm.core.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieTrailerResult

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult>
}
