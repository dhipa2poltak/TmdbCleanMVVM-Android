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
class GetMovieDetailsUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  @Before
  fun setup() {
    getMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieDetail method is called in AppRepository`() = runTest {
    val movieId = 101
    getMovieDetailsUseCase(movieId)
    verify(appRepository).getMovieDetail(movieId)
  }
}
