package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.Trailer
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerEntity

@Keep
data class TrailerResponse(
    val id: Int = 0,
    val results: List<Trailer> = arrayListOf()
)

fun TrailerResponse.toDomain(): TrailerDomain {
    val trailerEntities = results.map { TrailerEntity(it.id, it.name, it.key, it.site) }

    return TrailerDomain(this.id, trailerEntities.toList())
}
