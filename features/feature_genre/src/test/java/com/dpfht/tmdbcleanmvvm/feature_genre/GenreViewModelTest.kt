package com.dpfht.tmdbcleanmvvm.feature_genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.Genre
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.model.Result
import com.dpfht.tmdbcleanmvvm.feature_genre.adapter.GenreAdapter
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
class GenreViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: GenreViewModel

  @Mock
  private lateinit var adapter: GenreAdapter

  @Mock
  private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = GenreViewModel(adapter, getMovieGenreUseCase, arrayListOf())
  }

  @Test
  fun `fetch movie genre successfully`() = runTest {
    val genre1 = Genre(1, "Cartoon")
    val genre2 = Genre(2, "Drama")
    val genre3 = Genre(3, "Horror")

    val genres = listOf(genre1, genre2, genre3)
    val getMovieGenreResult = GenreModel(genres)
    val result = Result.Success(getMovieGenreResult)

    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.start()

    verify(adapter, times(genres.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie genre`() = runTest {
    val msg = "error fetch genre"
    val result = Result.Error(msg)

    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
