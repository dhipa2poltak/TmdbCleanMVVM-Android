package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.model.Result.Error
import com.dpfht.tmdbcleanmvvm.domain.model.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.model.Review
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
  val adapter: MovieReviewsAdapter,
  private val getMovieReviewUseCase: GetMovieReviewUseCase,
  private val reviews: ArrayList<Review>
): BaseViewModel() {

  private var _movieId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  protected val mIsEmptyResponse = MutableLiveData<Boolean>()
  val isEmptyResponse: LiveData<Boolean> = mIsEmptyResponse

  init {
    adapter.reviews = reviews
  }

  fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun getMovieReviews() {
    if (isEmptyNextResponse) return

    viewModelScope.launch {
      mIsShowDialogLoading.postValue(true)
      mIsLoadingData = true

      when (val result = getMovieReviewUseCase(_movieId, page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this@MovieReviewsViewModel.page = page

      for (review in reviews) {
        this@MovieReviewsViewModel.reviews.add(review)
        adapter.notifyItemInserted(this@MovieReviewsViewModel.reviews.size - 1)
      }
    } else {
      if (this@MovieReviewsViewModel.reviews.isEmpty()) {
        mIsEmptyResponse.postValue(true)
      }
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
