package com.dpfht.tmdbcleanmvvm.feature_movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dpfht.tmdbcleanmvvm.feature_movie_details.R.layout
import com.dpfht.tmdbcleanmvvm.feature_movie_details.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import com.dpfht.tmdbcleanmvvm.framework.R as frameworkR

@AndroidEntryPoint
class MovieDetailsFragment: BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>(layout.fragment_movie_details) {

  override val viewModel by viewModels<MovieDetailsViewModel>()

  override fun setupView(view: View, savedInstanceState: Bundle?) {
    binding.tvShowReview.setOnClickListener {
      onClickShowReview()
    }

    binding.tvShowTrailer.setOnClickListener {
      onClickShowTrailer()
    }
  }

  override fun observeViewModel() {
    viewModel.titleData.observe(viewLifecycleOwner) { title ->
      binding.tvTitleMovie.text = title
    }

    viewModel.overviewData.observe(viewLifecycleOwner) { overview ->
      binding.tvDescMovie.text = overview
    }

    viewModel.imageUrlData.observe(viewLifecycleOwner) { imageUrl ->
      Picasso.get().load(imageUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(frameworkR.drawable.loading)
        .into(binding.ivImageMovie)
    }

    viewModel.genres.observe(viewLifecycleOwner) { genres ->
      if (genres.isNotEmpty()) {
        binding.tvGenresMovie.text = genres
        binding.tvGenresMovie.visibility = View.VISIBLE
      } else {
        binding.tvGenresMovie.text = genres
        binding.tvGenresMovie.visibility = View.INVISIBLE
      }
    }
  }

  override fun startViewModel() {
    arguments?.let {
      val movieId = it.getInt("movieId")

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }

  private fun onClickShowReview() {
    navigationService.navigateToMovieReviews(viewModel.getMovieId(), viewModel.getMovieTitle())
  }

  private fun onClickShowTrailer() {
    navigationService.navigateToMovieTrailer(viewModel.getMovieId())
  }
}
