package com.dpfht.tmdbcleanmvvm.core.domain.usecase

import com.dpfht.tmdbcleanmvvm.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result

interface GetMovieByGenreUseCase {

  suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain>
}
