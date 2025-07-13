package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.TrailerResp
import com.dpfht.tmdbcleanmvvm.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvvm.domain.model.TrailerModel

@Keep
data class TrailerResponse(
    val id: Int? = -1,
    val results: List<TrailerResp>? = listOf()
)

fun TrailerResponse.toDomain(): TrailerModel {
    val trailerEntities = results?.map { it.toDomain() }

    return TrailerModel(this.id ?: -1, trailerEntities?.toList() ?: listOf())
}
