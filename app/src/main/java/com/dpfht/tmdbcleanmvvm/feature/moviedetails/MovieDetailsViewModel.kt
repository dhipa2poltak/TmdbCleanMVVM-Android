package com.dpfht.tmdbcleanmvvm.feature.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dpfht.tmdbcleanmvvm.feature.base.BaseViewModel
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  val getMovieDetailsUseCase: GetMovieDetailsUseCase
): BaseViewModel() {

  private var _movieId = -1
  private var title = ""
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

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    } else {
      _titleData.postValue(title)
      _overviewData.postValue(overview)
      _imageUrlData.postValue(imageUrl)
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch(Dispatchers.Main) {
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
    imageUrl = result.imageUrl

    _movieId = result.id
    title = result.title
    overview = result.overview

    _titleData.postValue(title)
    _overviewData.postValue(overview)
    _imageUrlData.postValue(imageUrl)

    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mErrorMessage.postValue(message)
  }

  fun getNavDirectionsToMovieReviews(): NavDirections {
    return MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(_movieId, title)
  }
}
