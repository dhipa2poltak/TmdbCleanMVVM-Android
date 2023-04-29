package com.dpfht.tmdbcleanmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.MainCoroutineRule
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.feature_movie_details.MovieDetailsViewModel
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
class MovieDetailsViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MovieDetailsViewModel

  @Mock
  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  @Mock
  private lateinit var titleObserver: Observer<String>

  @Mock
  private lateinit var overviewObserver: Observer<String>

  @Mock
  private lateinit var imageUrlObserver: Observer<String>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
  }

  @Test
  fun `fetch movie details data successfully`() = runBlocking {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = MovieDetailsDomain(
      id = movieId,
      title = title,
      overview = overview,
      imageUrl = posterPath
    )

    val result = Result.Success(getMovieDetailsResult)

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(result)

    viewModel.titleData.observeForever(titleObserver)
    viewModel.overviewData.observeForever(overviewObserver)
    viewModel.imageUrlData.observeForever(imageUrlObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(titleObserver).onChanged(eq(title))
    verify(overviewObserver).onChanged(eq(overview))

    val imageUrl = posterPath
    verify(imageUrlObserver).onChanged(eq(imageUrl))

    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie details`() = runBlocking {
    val msg = "error fetch movie details"
    val result = Result.ErrorResult(msg)

    val movieId = 1

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
