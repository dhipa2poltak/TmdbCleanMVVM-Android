package com.dpfht.tmdbcleanmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.MainCoroutineRule
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewEntity
import com.dpfht.tmdbcleanmvvm.feature.moviereviews.MovieReviewsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MovieReviewViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MovieReviewsViewModel

  @Mock
  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val listOfReview = arrayListOf<ReviewEntity>()

  @Before
  fun setup() {
    viewModel = MovieReviewsViewModel(getMovieReviewUseCase, listOfReview)
  }

  @Test
  fun `fetch movie review successfully`() = runBlocking {
    val review1 = ReviewEntity(author = "author1", content = "content1")
    val review2 = ReviewEntity(author = "author2", content = "content2")
    val reviews = listOf(review1, review2)

    val movieId = 1
    val page = 1

    val getMovieReviewResult = ReviewDomain(reviews)
    val result = Result.Success(getMovieReviewResult)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfReview.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie review`() = runBlocking {
    val movieId = 1
    val page = 1

    val msg = "error fetch movie review"
    val result = Result.ErrorResult(msg)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
