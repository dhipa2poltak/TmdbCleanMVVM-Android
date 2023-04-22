package com.dpfht.tmdbcleanmvvm.feature.movietrailer

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.PlayerConfig
import com.dpfht.tmdbcleanmvvm.R
import com.dpfht.tmdbcleanmvvm.databinding.ActivityMovieTrailerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class MovieTrailerActivity : YouTubeBaseActivity() {

  private lateinit var binding: ActivityMovieTrailerBinding
  lateinit var viewModel: MovieTrailerViewModel

  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface MovieTrailerEntryPoint {
    fun getMovieTrailerViewModel(): MovieTrailerViewModel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val entryPoint = EntryPointAccessors.fromApplication(applicationContext, MovieTrailerEntryPoint::class.java)
    viewModel = entryPoint.getMovieTrailerViewModel()

    binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (intent.hasExtra("movie_id")) {
      val movieId = intent.getIntExtra("movie_id", -1)

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }

  private val observerKeyVideo = Observer<String> { keyVideo ->
    keyVideo?.let {
      showTrailer(it)
    }
  }

  private val observerErrorMessage = Observer<String> { message ->
    message?.let {
      if (it.isNotEmpty()) {
        showErrorMessage(it)
      }
    }
  }

  private val observerShowCanceledMessage = Observer<Boolean> { show ->
    if (show == true) {
      showCanceledMessage()
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.keyVideo.observeForever(observerKeyVideo)
    viewModel.errorMessage.observeForever(observerErrorMessage)
    viewModel.showCanceledMessage.observeForever(observerShowCanceledMessage)
  }

  override fun onPause() {
    super.onPause()
    viewModel.keyVideo.removeObserver(observerKeyVideo)
    viewModel.errorMessage.removeObserver(observerErrorMessage)
    viewModel.showCanceledMessage.removeObserver(observerShowCanceledMessage)
  }

  private fun showTrailer(keyVideo: String) {
    binding.playerYoutube.initialize(
      PlayerConfig.API_KEY,
      object : YouTubePlayer.OnInitializedListener {

        override fun onInitializationSuccess(
          p0: YouTubePlayer.Provider?,
          youtubePlayer: YouTubePlayer?,
          p2: Boolean
        ) {
          youtubePlayer?.loadVideo(keyVideo)
          youtubePlayer?.play()
        }

        override fun onInitializationFailure(
          p0: YouTubePlayer.Provider?,
          p1: YouTubeInitializationResult?
        ) {
          Toast.makeText(
            baseContext,
            "error loading youtube video",
            Toast.LENGTH_SHORT
          ).show()
        }
      })
  }

  private fun showErrorMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }

  override fun onDestroy() {
    viewModel.onDestroy()
    super.onDestroy()
  }
}
