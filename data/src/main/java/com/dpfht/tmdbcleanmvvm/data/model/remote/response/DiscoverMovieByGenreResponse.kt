package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.MovieDTO
import com.dpfht.tmdbcleanmvvm.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvvm.domain.model.DiscoverMovieByGenreModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class DiscoverMovieByGenreResponse(
    val page: Int? = -1,
    val results: List<MovieDTO>? = listOf(),

    @SerializedName("total_pages")
  @Expose
  val totalPages: Int? = -1,

    @SerializedName("total_results")
  @Expose
  val totalResults: Int? = -1
)

fun DiscoverMovieByGenreResponse.toDomain(): DiscoverMovieByGenreModel {
  val movieEntities = results?.map {
    it.toDomain()
  }

  return DiscoverMovieByGenreModel(
    page ?: -1,
    movieEntities?.toList() ?: listOf(),
    totalPages ?: -1,
    totalResults ?: -1
  )
}
