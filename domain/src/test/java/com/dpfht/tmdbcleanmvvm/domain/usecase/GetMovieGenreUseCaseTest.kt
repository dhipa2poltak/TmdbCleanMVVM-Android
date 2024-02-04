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
class GetMovieGenreUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

  @Before
  fun setup() {
    getMovieGenreUseCase = GetMovieGenreUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieGenre method is called in AppRepository`() = runTest {
    getMovieGenreUseCase()

    verify(appRepository).getMovieGenre()
  }
}
