package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
  val getMovieReviewUseCase: GetMovieReviewUseCase,
  val reviews: ArrayList<ReviewEntity>
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
          onSuccess(result.value.results, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<ReviewEntity>, page: Int) {
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