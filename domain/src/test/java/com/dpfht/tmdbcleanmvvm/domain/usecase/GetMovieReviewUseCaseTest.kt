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
class GetMovieReviewUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  @Before
  fun setup() {
    getMovieReviewUseCase = GetMovieReviewUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieReviews method is called in AppRepository`() = runTest {
    val movieId = 101
    val page = 1

    getMovieReviewUseCase(movieId, page)

    verify(appRepository).getMovieReviews(movieId, page)
  }
}
