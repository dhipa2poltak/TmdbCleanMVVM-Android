package com.dpfht.tmdbcleanmvvm.feature_movie_trailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.model.Result.Error
import com.dpfht.tmdbcleanmvvm.domain.model.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.model.Trailer
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MovieTrailerViewModel @Inject constructor(
  private val getMovieTrailerUseCase: GetMovieTrailerUseCase
): BaseViewModel() {

  private var _movieId = -1

  private val _keyVideo = MutableLiveData<String>()
  val keyVideo: LiveData<String> = _keyVideo

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    viewModelScope.launch {
      when (val result = getMovieTrailerUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value.results)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(trailers: List<Trailer>) {
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
}
