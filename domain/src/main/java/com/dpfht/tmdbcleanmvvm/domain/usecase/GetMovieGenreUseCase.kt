package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.Result

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreDomain>
}
