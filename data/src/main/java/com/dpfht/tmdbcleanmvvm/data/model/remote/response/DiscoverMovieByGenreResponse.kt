package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.Constants
import com.dpfht.tmdbcleanmvvm.data.model.remote.Movie
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class DiscoverMovieByGenreResponse(
  val page: Int = -1,
  val results: List<Movie> = arrayListOf(),

  @SerializedName("total_pages")
  @Expose
  val totalPages: Int = -1,

  @SerializedName("total_results")
  @Expose
  val totalResults: Int = -1
)

fun DiscoverMovieByGenreResponse.toDomain(): DiscoverMovieByGenreDomain {
  val movieEntities = results.map {
    MovieEntity(
      it.id,
      it.title,
      it.overview,
      if (it.posterPath.isNotEmpty()) Constants.IMAGE_URL_BASE_PATH + it.posterPath else ""
    )
  }

  return DiscoverMovieByGenreDomain(
    page, movieEntities.toList(), totalPages, totalResults
  )
}