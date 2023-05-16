package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.Trailer
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerEntity

@Keep
data class TrailerResponse(
    val id: Int? = -1,
    val results: List<Trailer>? = listOf()
)

fun TrailerResponse.toDomain(): TrailerDomain {
    val trailerEntities = results?.map { TrailerEntity(it.id ?: "", it.key ?: "", it.name ?: "", it.site ?: "") }

    return TrailerDomain(this.id ?: -1, trailerEntities?.toList() ?: listOf())
}
