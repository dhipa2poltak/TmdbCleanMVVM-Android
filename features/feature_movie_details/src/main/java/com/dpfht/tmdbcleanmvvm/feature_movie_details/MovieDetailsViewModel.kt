package com.dpfht.tmdbcleanmvvm.feature_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(result: MovieDetailsDomain) {
    viewModelScope.launch(Dispatchers.Main) {
      imageUrl = result.imageUrl

      _movieId = result.id
      _title = result.title
      overview = result.overview

      _titleData.value = _title
      _overviewData.value = overview
      _imageUrlData.value = imageUrl

      mIsShowDialogLoading.value = false
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch(Dispatchers.Main) {
      mIsShowDialogLoading.value = false
      mErrorMessage.value = message
    }
  }
}
