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
class GetMovieByGenreUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Before
  fun setup() {
    getMovieByGenreUseCase = GetMovieByGenreUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMoviesByGenre method is called in AppRepository class`() = runTest {
    val genreId = 101
    val page = 1

    getMovieByGenreUseCase(genreId, page)

    verify(appRepository).getMoviesByGenre("$genreId", page)
  }
}
