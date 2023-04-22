package com.dpfht.tmdbcleanmvvm.feature.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dpfht.tmdbcleanmvvm.feature.base.BaseViewModel
import com.dpfht.tmdbcleanmvvm.core.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvvm.core.domain.entity.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
  val getMovieGenreUseCase: GetMovieGenreUseCase,
  val genres: ArrayList<GenreEntity>
) : BaseViewModel() {

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int> = _notifyItemInserted

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    viewModelScope.launch(Dispatchers.Main) {
      mIsShowDialogLoading.postValue(true)
      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccess(result.value.genres)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(genres: List<GenreEntity>) {
    for (genre in genres) {
      this.genres.add(genre)
      _notifyItemInserted.postValue(this.genres.size - 1)
    }
    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    mErrorMessage.postValue(message)
  }

  fun getNavDirectionsOnClickGenreAt(position: Int): NavDirections {
    val genre = genres[position]

    return GenreFragmentDirections.actionGenreFragmentToMoviesByGenreFragment(
      genre.id, genre.name
    )
  }
}
