package com.dpfht.tmdbcleanmvvm.data.repository

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource
): AppRepository {

  override suspend fun getMovieGenre(): GenreDomain {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerDomain {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
