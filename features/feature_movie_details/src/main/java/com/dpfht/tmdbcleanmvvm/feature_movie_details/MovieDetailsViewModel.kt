package com.dpfht.tmdbcleanmvvm.feature_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  val getMovieDetailsUseCase: GetMovieDetailsUseCase
): BaseViewModel() {

  private var _movieId = -1
  private var _title = ""
  private var overview = ""
  private var imageUrl = ""

  //--

  private val _titleData = MutableLiveData<String>()
  val titleData: LiveData<String> = _titleData

  private val _overviewData = MutableLiveData<String>()
  val overviewData: LiveData<String> = _overviewData

  private val _imageUrlData = MutableLiveData<String>()
  val imageUrlData: LiveData<String> = _imageUrlData

  private val _genres = MutableLiveData<String>()
  val genres: LiveData<String> = _genres

  //--

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun getMovieId(): Int {
    return _movieId
  }

  fun getMovieTitle(): String {
    return _title
  }

  override fun start() {
    if (_title.isEmpty()) {
      getMovieDetails()
    } else {
      _titleData.postValue(_title)
      _overviewData.postValue(overview)
      _imageUrlData.postValue(imageUrl)
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch {
      mIsShowDialogLoading.postValue(true)
      when (val result = getMovieDetailsUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(movie: MovieDetailsDomain) {
    viewModelScope.launch {
      imageUrl = movie.imageUrl

      _movieId = movie.id
      _title = movie.title
      overview = movie.overview

      _titleData.postValue(_title)
      _overviewData.postValue(overview)
      _imageUrlData.postValue(imageUrl)

      var strGenres = ""
      for (genre in movie.genres) {
        if (strGenres.isEmpty()) {
          strGenres = genre.name
        } else {
          strGenres += ", ${genre.name}"
        }
      }

      _genres.value = strGenres

      mIsShowDialogLoading.postValue(false)
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch {
      mIsShowDialogLoading.postValue(false)
      mErrorMessage.postValue(message)
    }
  }
}
