package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewEntity
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter.MovieReviewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieReviewsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MovieReviewsViewModel

  @Mock
  private lateinit var adapter: MovieReviewsAdapter

  @Mock
  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieReviewsViewModel(adapter, getMovieReviewUseCase, arrayListOf())
  }

  @Test
  fun `fetch movie review successfully`() = runTest {
    val review1 = ReviewEntity(author = "author1", content = "content1")
    val review2 = ReviewEntity(author = "author2", content = "content2")
    val reviews = listOf(review1, review2)

    val movieId = 1
    val page = 1

    val getMovieReviewResult = ReviewDomain(reviews)
    val result = Result.Success(getMovieReviewResult)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(adapter, times(reviews.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie review`() = runTest {
    val movieId = 1
    val page = 1

    val msg = "error fetch movie review"
    val result = Result.Error(msg)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
