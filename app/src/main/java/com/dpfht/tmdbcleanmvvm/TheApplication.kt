package com.dpfht.tmdbcleanmvvm

import android.app.Application
import com.dpfht.tmdbcleanmvvm.framework.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication: Application() {

  override fun onCreate() {
    Config.BASE_URL = BuildConfig.BASE_URL
    super.onCreate()
    instance = this
  }

  companion object {
    lateinit var instance: TheApplication
  }
}
