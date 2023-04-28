package com.dpfht.tmdbcleanmvvm.feature.moviesbygenre

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
  val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  val movies: ArrayList<MovieEntity>
): BaseViewModel() {

  private var _genreId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int> = _notifyItemInserted

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

    viewModelScope.launch(Dispatchers.Main) {
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
    if (movies.isNotEmpty()) {
      this.page = page

      for (movie in movies) {
        this.movies.add(movie)
        _notifyItemInserted.postValue(this.movies.size - 1)
      }
    } else {
      isEmptyNextResponse = true
    }

    mIsShowDialogLoading.postValue(false)
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mIsLoadingData = false
    mErrorMessage.postValue(message)
  }

  fun getNavDeeplinkRequestOnClickMovieAt(position: Int): NavDeepLinkRequest {
    val movie = movies[position]

    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("tmdbcleanmvvm.dpfht.com")
      .appendPath("movie_details_fragment")
      .appendQueryParameter("movieId", "${movie.id}")

    return NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build()
  }
}
