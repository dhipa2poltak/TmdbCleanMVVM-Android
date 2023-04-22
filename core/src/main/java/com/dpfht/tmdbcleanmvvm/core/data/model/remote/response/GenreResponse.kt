package com.dpfht.tmdbcleanmvvm.core.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreEntity

@Keep
data class GenreResponse(
    val genres: List<Genre> = arrayListOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres.map { GenreEntity(it.id, it.name) }

    return GenreDomain(genreEntities.toList())
}
