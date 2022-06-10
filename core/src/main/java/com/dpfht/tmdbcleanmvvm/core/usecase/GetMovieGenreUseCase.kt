package com.dpfht.tmdbcleanmvvm.core.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult>
}
