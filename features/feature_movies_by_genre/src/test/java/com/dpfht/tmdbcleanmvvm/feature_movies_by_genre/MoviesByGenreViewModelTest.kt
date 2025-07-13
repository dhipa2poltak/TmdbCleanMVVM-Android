package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.Movie
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.model.Result
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.adapter.MoviesByGenreAdapter
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
class MoviesByGenreViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MoviesByGenreViewModel

  @Mock
  private lateinit var adapter: MoviesByGenreAdapter

  @Mock
  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MoviesByGenreViewModel(adapter, getMovieByGenreUseCase, arrayListOf())
  }

  @Test
  fun `fetch movie successfully`() = runTest {
    val movie1 = Movie(id = 1, title = "title1", overview = "overview1")
    val movie2 = Movie(id = 2, title = "title2", overview = "overview2")
    val movie3 = Movie(id = 3, title = "title3", overview = "overview3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = DiscoverMovieByGenreModel(page, movies)
    val result = Result.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(adapter, times(movies.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie`() = runTest {
    val msg = "error fetch movie"
    val result = Result.Error(msg)

    val genreId = 1
    val page = 1

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
