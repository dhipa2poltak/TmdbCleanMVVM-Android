package com.dpfht.tmdbcleanmvvm.feature_splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvvm.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BaseViewModel() {

  private val _hasFinishedDelaying = MutableLiveData<Boolean>()
  val hasFinishedDelaying: LiveData<Boolean> = _hasFinishedDelaying

  override fun start() {
    viewModelScope.launch {
      delay(3000)
      _hasFinishedDelaying.postValue(true)
    }
  }
}
