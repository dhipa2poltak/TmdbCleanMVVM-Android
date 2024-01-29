package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.data.model.remote.response.toDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.RestService
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.safeApiCall
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(private val restService: RestService):
  AppDataSource {

  override suspend fun getMovieGenre(): Result<GenreDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieGenre().toDomain() }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): Result<DiscoverMovieByGenreDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page).toDomain() }
  }

  override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailsDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId).toDomain() }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): Result<ReviewDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page).toDomain() }
  }

  override suspend fun getMovieTrailer(movieId: Int): Result<TrailerDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId).toDomain() }
  }
}
