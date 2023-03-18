package com.dpfht.tmdbcleanmvvm.core.data.repository

import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvvm.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper

class AppRepositoryImpl(private val remoteDataSource: AppDataSource): AppRepository {

  override suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult> {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
