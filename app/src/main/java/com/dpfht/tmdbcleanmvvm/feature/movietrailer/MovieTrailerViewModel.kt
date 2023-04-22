package com.dpfht.tmdbcleanmvvm.feature.movietrailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.core.domain.entity.TrailerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class MovieTrailerViewModel(
  val getMovieTrailerUseCase: GetMovieTrailerUseCase,
  private val scope: CoroutineScope
) {

  private var _movieId = -1

  private val _keyVideo = MutableLiveData<String>()
  val keyVideo: LiveData<String> = _keyVideo

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = mErrorMessage

  private val mShowCanceledMessage = MutableLiveData<Boolean>()
  val showCanceledMessage: LiveData<Boolean> = mShowCanceledMessage

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    scope.launch(Dispatchers.Main) {
      when (val result = getMovieTrailerUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value.results)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(trailers: List<TrailerEntity>) {
    var keyVideo = ""
    for (trailer in trailers) {
      if (trailer.site.lowercase(Locale.ROOT).trim() == "youtube"
      ) {
        keyVideo = trailer.key
        break
      }
    }

    if (keyVideo.isNotEmpty()) {
      _keyVideo.postValue(keyVideo)
    }
  }

  private fun onError(message: String) {
    mErrorMessage.postValue(message)
  }

  fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
  }
}
