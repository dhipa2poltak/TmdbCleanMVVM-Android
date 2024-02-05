package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.Genre
import com.dpfht.tmdbcleanmvvm.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain

@Keep
data class GenreResponse(
    val genres: List<Genre>? = listOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres?.map { it.toDomain() }

    return GenreDomain(genreEntities?.toList() ?: listOf())
}
