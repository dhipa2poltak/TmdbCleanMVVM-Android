package com.dpfht.tmdbcleanmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.MainCoroutineRule
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerEntity
import com.dpfht.tmdbcleanmvvm.feature.movietrailer.MovieTrailerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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
class MovieTrailerViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MovieTrailerViewModel

  @Mock
  private lateinit var getMovieTrailerUseCase: com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieTrailerUseCase

  @Mock
  private lateinit var keyVideoObserver: Observer<String>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val scope = CoroutineScope(Job())

  @Before
  fun setup() {
    viewModel = MovieTrailerViewModel(getMovieTrailerUseCase, scope)
  }

  @Test
  fun `fetch movie trailer successfully`() = runBlocking {
    val keyVideo1 = "11111"
    val trailer1 = TrailerEntity(id = "1", key = keyVideo1, name = "name1", site = "youtube")
    val trailer2 = TrailerEntity(id = "2", key = "22222", name = "name2", site = "youtube")
    val trailer3 = TrailerEntity(id = "3", key = "33333", name = "name3", site = "youtube")

    val trailers = listOf(trailer1, trailer2, trailer3)
    val getMovieTrailerResult = TrailerDomain(results = trailers)
    val result = Result.Success(getMovieTrailerResult)

    val movieId = 1

    whenever(getMovieTrailerUseCase.invoke(movieId)).thenReturn(result)

    viewModel.keyVideo.observeForever(keyVideoObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(keyVideoObserver).onChanged(eq(keyVideo1))
  }

  @Test
  fun `failed fetch movie trailer`() = runBlocking {
    val msg = "error fetch movie trailer"
    val result = Result.ErrorResult(msg)

    val movieId = 1

    whenever(getMovieTrailerUseCase.invoke(movieId)).thenReturn(result)

    viewModel.errorMessage.observeForever(errorMessageObserver)

    viewModel.setMovieId(movieId)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
  }
}
