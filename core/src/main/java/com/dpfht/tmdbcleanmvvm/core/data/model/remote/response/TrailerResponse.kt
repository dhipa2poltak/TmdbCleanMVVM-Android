package com.dpfht.tmdbcleanmvvm.core.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Trailer
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerEntity

@Keep
data class TrailerResponse(
    val id: Int = 0,
    val results: List<Trailer> = arrayListOf()
)

fun TrailerResponse.toDomain(): TrailerDomain {
    val trailerEntities = results.map { TrailerEntity(it.id, it.name, it.key, it.site) }

    return TrailerDomain(this.id, trailerEntities.toList())
}
