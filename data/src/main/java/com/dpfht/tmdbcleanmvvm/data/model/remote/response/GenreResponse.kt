package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.GenreDTO
import com.dpfht.tmdbcleanmvvm.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel

@Keep
data class GenreResponse(
    val genres: List<GenreDTO>? = listOf()
)

fun GenreResponse.toDomain(): GenreModel {
    val genreEntities = genres?.map { it.toDomain() }

    return GenreModel(genreEntities?.toList() ?: listOf())
}
