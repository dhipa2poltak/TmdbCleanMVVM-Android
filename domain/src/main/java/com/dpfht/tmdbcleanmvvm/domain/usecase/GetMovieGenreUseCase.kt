package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.Result

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreModel>
}
