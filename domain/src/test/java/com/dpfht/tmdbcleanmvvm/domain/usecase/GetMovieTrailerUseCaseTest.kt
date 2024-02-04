package com.dpfht.tmdbcleanmvvm.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieTrailerUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieTrailerUseCase: GetMovieTrailerUseCase

  @Before
  fun setup() {
    getMovieTrailerUseCase = GetMovieTrailerUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieTrailer method in AppRepository is called`() = runTest {
    val movieId = 101
    getMovieTrailerUseCase(movieId)
    verify(appRepository).getMovieTrailer(movieId)
  }
}
