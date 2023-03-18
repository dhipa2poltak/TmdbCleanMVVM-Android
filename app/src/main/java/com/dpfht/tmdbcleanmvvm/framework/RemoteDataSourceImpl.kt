package com.dpfht.tmdbcleanmvvm.framework

import com.dpfht.tmdbcleanmvvm.core.data.model.remote.response.toDomain
import com.dpfht.tmdbcleanmvvm.core.data.repository.AppDataSource
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbcleanmvvm.framework.rest.RestService
import com.dpfht.tmdbcleanmvvm.framework.rest.ResultWrapper.GenericError
import com.dpfht.tmdbcleanmvvm.framework.rest.ResultWrapper.NetworkError
import com.dpfht.tmdbcleanmvvm.framework.rest.ResultWrapper.Success
import com.dpfht.tmdbcleanmvvm.framework.rest.api.safeApiCall
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(private val restService: RestService):
  AppDataSource {

  override suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }
}