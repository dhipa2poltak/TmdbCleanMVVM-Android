package com.dpfht.tmdbcleanmvvm.feature.moviereviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.feature.base.BaseViewModel
import com.dpfht.tmdbcleanmvvm.core.data.model.remote.Review
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
  val getMovieReviewUseCase: GetMovieReviewUseCase,
  val reviews: ArrayList<Review>
): BaseViewModel() {

  private var _movieId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int> = _notifyItemInserted

  override fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun getMovieReviews() {
    if (isEmptyNextResponse) return

    viewModelScope.launch(Dispatchers.Main) {
      mIsShowDialogLoading.postValue(true)
      mIsLoadingData = true

      when (val result = getMovieReviewUseCase(_movieId, page + 1)) {
        is Success -> {
          onSuccess(result.value.reviews, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        _notifyItemInserted.postValue(this.reviews.size - 1)
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
}
