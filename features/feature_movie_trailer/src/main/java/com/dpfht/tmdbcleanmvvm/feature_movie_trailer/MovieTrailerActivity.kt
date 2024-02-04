package com.dpfht.tmdbcleanmvvm.feature_movie_trailer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dpfht.tmdbcleanmvvm.feature_movie_trailer.databinding.ActivityMovieTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieTrailerActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMovieTrailerBinding
  private val viewModel by viewModels<MovieTrailerViewModel>()

  private lateinit var youTubePlayer: YouTubePlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.youtubePlayerView.enableAutomaticInitialization = false
    lifecycle.addObserver(binding.youtubePlayerView)

    if (intent.hasExtra("movie_id")) {
      val movieId = intent.getIntExtra("movie_id", -1)

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }

  private val observerKeyVideo = Observer<String> { keyVideo ->
    showTrailer(keyVideo)
  }

  private val observerErrorMessage = Observer<String> { message ->
    message.let {
      if (it.isNotEmpty()) {
        showErrorMessage(it)
      }
    }
  }

  private val observerShowCanceledMessage = Observer<Boolean> { show ->
    if (show) {
      showCanceledMessage()
    }
  }

  override fun onResume() {
    super.onResume()
    supportActionBar?.hide()
    viewModel.keyVideo.observeForever(observerKeyVideo)
    viewModel.errorMessage.observeForever(observerErrorMessage)
    viewModel.showCanceledMessage.observeForever(observerShowCanceledMessage)
  }

  override fun onPause() {
    super.onPause()
    supportActionBar?.show()
    viewModel.keyVideo.removeObserver(observerKeyVideo)
    viewModel.errorMessage.removeObserver(observerErrorMessage)
    viewModel.showCanceledMessage.removeObserver(observerShowCanceledMessage)
  }

  private fun showTrailer(keyVideo: String) {
    val iFramePlayerOptions = IFramePlayerOptions.Builder()
      .controls(1)
      .build()

    binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
      override fun onReady(youTubePlayer: YouTubePlayer) {
        super.onReady(youTubePlayer)

        this@MovieTrailerActivity.youTubePlayer = youTubePlayer
        youTubePlayer.loadVideo(keyVideo, 0f)
      }
    }, iFramePlayerOptions)
  }

  private fun showErrorMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.trailer_text_canceled_message))
  }
}
