package com.dpfht.tmdbcleanmvvm.core.data.model.remote.response

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Trailer
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieTrailerResult

data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)

fun TrailerResponse.toDomain() = GetMovieTrailerResult(this.results ?: arrayListOf())
