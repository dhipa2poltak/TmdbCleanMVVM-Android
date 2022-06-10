package com.dpfht.tmdbcleanmvvm.core.data.model.remote.response

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult

data class GenreResponse(
    val genres: List<Genre>? = null
)

fun GenreResponse.toDomain() = GetMovieGenreResult(genres ?: arrayListOf())
