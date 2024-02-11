package com.dpfht.tmdbcleanmvvm.feature_movie_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
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
@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

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

  @Mock
  private lateinit var genresObserver: Observer<String>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
  }

  @Test
  fun `fetch movie details data successfully`() = runTest {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"
    val genreName1 = "Action"
    val genreName2 = "Drama"
    val genres = listOf(GenreEntity(10, genreName1), GenreEntity(11, genreName2))

    val getMovieDetailsResult = MovieDetailsDomain(
      id = movieId,
      title = title,
      overview = overview,
      imageUrl = posterPath,
      genres = genres
    )

    val result = Result.Success(getMovieDetailsResult)

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(result)

    viewModel.titleData.observeForever(titleObserver)
    viewModel.overviewData.observeForever(overviewObserver)
    viewModel.imageUrlData.observeForever(imageUrlObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.genres.observeForever(genresObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(titleObserver).onChanged(eq(title))
    verify(overviewObserver).onChanged(eq(overview))
    verify(imageUrlObserver).onChanged(eq(posterPath))
    verify(showLoadingObserver).onChanged(eq(false))
    verify(genresObserver).onChanged("$genreName1, $genreName2")

    assertTrue(viewModel.getMovieId() == movieId)
    assertTrue(viewModel.getMovieTitle() == title)

    viewModel.start()
    assertTrue(viewModel.titleData.value == title)
    assertTrue(viewModel.overviewData.value == overview)
    assertTrue(viewModel.imageUrlData.value == posterPath)
  }

  @Test
  fun `failed fetch movie details`() = runTest {
    val msg = "error fetch movie details"
    val result = Result.Error(msg)

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
