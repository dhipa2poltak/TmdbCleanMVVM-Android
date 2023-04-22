package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreDomain>
}
