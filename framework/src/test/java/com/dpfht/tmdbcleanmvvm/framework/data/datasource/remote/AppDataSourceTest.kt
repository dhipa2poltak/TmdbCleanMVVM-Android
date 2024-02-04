package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote

import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.RestService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppDataSourceTest {

  private lateinit var appDataSource: AppDataSource

  @Mock
  private lateinit var restService: RestService

  @Before
  fun setup() {
    appDataSource = RemoteDataSourceImpl(restService)
  }

  @Test
  fun `ensure getMovieGenre method is called in RestService`() = runTest {
    appDataSource.getMovieGenre()
    verify(restService).getMovieGenre()
  }

  @Test
  fun `ensure getMoviesByGenre method is called in RestService`() = runTest {
    val genreId = 10
    val page = 1

    appDataSource.getMoviesByGenre("$genreId", page)

    verify(restService).getMoviesByGenre("$genreId", page)
  }

  @Test
  fun `ensure getMovieDetail method is called in RestService`() = runTest {
    val movieId = 101
    appDataSource.getMovieDetail(movieId)
    verify(restService).getMovieDetail(movieId)
  }

  @Test
  fun `ensure getMovieReviews method is called in RestService`() = runTest {
    val movieId = 101
    val page = 1

    appDataSource.getMovieReviews(movieId, page)

    verify(restService).getMovieReviews(movieId, page)
  }

  @Test
  fun `ensure getMovieTrailer method is called in RestService`() = runTest {
    val movieId = 101
    appDataSource.getMovieTrailer(movieId)
    verify(restService).getMovieTrailers(movieId)
  }
}
