package com.dpfht.tmdbcleanmvvm.viewmodel

/*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.MainCoroutineRule
import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.MoviesByGenreViewModel
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
class MovieByGenreViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MoviesByGenreViewModel

  @Mock
  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val listOfMovie = arrayListOf<MovieEntity>()

  @Before
  fun setup() {
    viewModel = MoviesByGenreViewModel(getMovieByGenreUseCase, listOfMovie)
  }

  @Test
  fun `fetch movie successfully`() = runBlocking {
    val movie1 = MovieEntity(id = 1, title = "title1", overview = "overview1")
    val movie2 = MovieEntity(id = 2, title = "title2", overview = "overview2")
    val movie3 = MovieEntity(id = 3, title = "title3", overview = "overview3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = DiscoverMovieByGenreDomain(page, movies)
    val result = Result.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.setGenreId(genreId)
    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfMovie.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie`() = runBlocking {
    val msg = "error fetch movie"
    val result = Result.ErrorResult(msg)

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
*/