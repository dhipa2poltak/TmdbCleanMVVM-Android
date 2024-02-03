package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
  val adapter: MoviesByGenreAdapter,
  val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  val movies: ArrayList<MovieEntity>
): BaseViewModel() {

  private var _genreId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  init {
    adapter.movies = movies
  }

  override fun start() {
    if (_genreId != -1 && movies.isEmpty()) {
      getMoviesByGenre()
    }
  }

  fun setGenreId(genreId: Int) {
    this._genreId = genreId
  }

  fun getMoviesByGenre() {
    if (isEmptyNextResponse) return

    mIsShowDialogLoading.postValue(true)
    mIsLoadingData = true

    viewModelScope.launch {
      when (val result = getMovieByGenreUseCase(_genreId, page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(movies: List<MovieEntity>, page: Int) {
    viewModelScope.launch(Dispatchers.Main) {
      if (movies.isNotEmpty()) {
        this@MoviesByGenreViewModel.page = page

        for (movie in movies) {
          this@MoviesByGenreViewModel.movies.add(movie)
          adapter.notifyItemInserted(this@MoviesByGenreViewModel.movies.size - 1)
        }
      } else {
        isEmptyNextResponse = true
      }

      mIsShowDialogLoading.value = false
      mIsLoadingData = false
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch(Dispatchers.Main) {
      mIsShowDialogLoading.value = false
      mIsLoadingData = false
      mErrorMessage.value = message
    }
  }
}
