package com.dpfht.tmdbcleanmvvm.data.repository

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvvm.domain.model.TrailerModel

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource
): AppRepository {

  override suspend fun getMovieGenre(): GenreModel {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreModel {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsModel {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewModel {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerModel {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
