package com.dpfht.tmdbcleanmvvm.core.data.repository

import com.dpfht.tmdbcleanmvvm.core.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerDomain

class AppRepositoryImpl(private val remoteDataSource: AppDataSource): AppRepository {

  override suspend fun getMovieGenre(): Result<GenreDomain> {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): Result<DiscoverMovieByGenreDomain> {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailsDomain> {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): Result<ReviewDomain> {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): Result<TrailerDomain> {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
