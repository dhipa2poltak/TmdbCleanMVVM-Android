package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest

import com.dpfht.tmdbcleanmvvm.data.model.remote.response.ApiErrorResponse
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
  return withContext(dispatcher) {
    try {
      Success(apiCall.invoke())
    } catch (t: Throwable) {
      when (t) {
        is IOException -> ErrorResult("error in connection")
        is HttpException -> {
          //val code = t.code()
          val errorResponse = convertErrorBody(t)

          ErrorResult(errorResponse?.statusMessage ?: "http error")
        }
        else -> {
          ErrorResult("error in conversion")
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
