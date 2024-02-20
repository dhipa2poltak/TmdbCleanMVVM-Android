package com.dpfht.tmdbcleanmvvm.feature_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvvm.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvvm.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvvm.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
  val adapter: GenreAdapter,
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
  val genres: ArrayList<GenreEntity>
) : BaseViewModel() {

  init {
    adapter.genres = genres
  }

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    viewModelScope.launch {
      mIsShowDialogLoading.postValue(true)
      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccess(result.value.genres)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(genres: List<GenreEntity>) {
    for (genre in genres) {
      this@GenreViewModel.genres.add(genre)
      adapter.notifyItemInserted(this@GenreViewModel.genres.size - 1)
    }

    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mErrorMessage.postValue(message)
  }
}
