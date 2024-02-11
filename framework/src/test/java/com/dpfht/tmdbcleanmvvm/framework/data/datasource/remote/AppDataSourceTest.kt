package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote

import android.content.Context
import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.domain.entity.AppException
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.RestService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppDataSourceTest {

  private lateinit var appDataSource: AppDataSource

  @Mock
  private lateinit var restService: RestService

  @Mock
  private lateinit var context: Context

  private val message = "this is a test"

  @Before
  fun setup() {
    appDataSource = RemoteDataSourceImpl(context, restService)

    `when`(context.getString(anyInt())).thenReturn(message)
  }

  @Test
  fun `ensure getMovieGenre method is called in RestService`() = runTest {
    try {
      appDataSource.getMovieGenre()
    } catch (e: AppException) {
      assertEquals(message, e.message)
    }

    verify(restService).getMovieGenre()
  }

  @Test
  fun `ensure getMoviesByGenre method is called in RestService`() = runTest {
    val genreId = 10
    val page = 1

    try {
      appDataSource.getMoviesByGenre("$genreId", page)
    } catch (e: AppException) {
      assertEquals(message, e.message)
    }

    verify(restService).getMoviesByGenre("$genreId", page)
  }

  @Test
  fun `ensure getMovieDetail method is called in RestService`() = runTest {
    val movieId = 101

    try {
      appDataSource.getMovieDetail(movieId)
    } catch (e: AppException) {
      assertEquals(message, e.message)
    }

    verify(restService).getMovieDetail(movieId)
  }

  @Test
  fun `ensure getMovieReviews method is called in RestService`() = runTest {
    val movieId = 101
    val page = 1

    try {
      appDataSource.getMovieReviews(movieId, page)
    } catch (e: AppException) {
      assertEquals(message, e.message)
    }

    verify(restService).getMovieReviews(movieId, page)
  }

  @Test
  fun `ensure getMovieTrailer method is called in RestService`() = runTest {
    val movieId = 101

    try {
      appDataSource.getMovieTrailer(movieId)
    } catch (e: AppException) {
      assertEquals(message, e.message)
    }

    verify(restService).getMovieTrailers(movieId)
  }
}
