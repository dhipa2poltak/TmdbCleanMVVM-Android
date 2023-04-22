package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.Genre
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreEntity

@Keep
data class GenreResponse(
    val genres: List<Genre> = arrayListOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres.map { GenreEntity(it.id, it.name) }

    return GenreDomain(genreEntities.toList())
}
