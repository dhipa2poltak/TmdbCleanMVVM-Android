package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.data.model.remote.response.ApiErrorResponse
import com.dpfht.tmdbcleanmvvm.data.model.remote.response.toDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.RestService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

class RemoteDataSourceImpl(
  private val restService: RestService
): AppDataSource {

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

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
      try {
        Result.Success(apiCall.invoke())
      } catch (t: Throwable) {
        when (t) {
          is IOException -> Result.ErrorResult("error in connection")
          is HttpException -> {
            //val code = t.code()
            val errorResponse = convertErrorBody(t)

            Result.ErrorResult(errorResponse?.statusMessage ?: "http error")
          }
          else -> {
            Result.ErrorResult("error in conversion")
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ApiErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ApiErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ApiErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
