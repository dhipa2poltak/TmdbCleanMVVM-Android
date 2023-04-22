package com.dpfht.tmdbcleanmvvm.feature.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dpfht.tmdbcleanmvvm.R
import com.dpfht.tmdbcleanmvvm.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbcleanmvvm.feature.movietrailer.MovieTrailerActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment: Fragment() {

  private lateinit var binding: FragmentMovieDetailsBinding
  private val viewModel by viewModels<MovieDetailsViewModel>()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.tvShowReview.setOnClickListener {
      onClickShowReview()
    }

    binding.tvShowTrailer.setOnClickListener {
      onClickShowTrailer()
    }

    //--

    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { value ->
      if (value) {
        binding.pbLoading.visibility = View.VISIBLE
      } else {
        binding.pbLoading.visibility = View.GONE
      }
    }

    viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
      if (message.isNotEmpty()) {
        showErrorMessage(message)
      }
    }

    viewModel.showCanceledMessage.observe(viewLifecycleOwner) { isShow ->
      if (isShow) {
        showCanceledMessage()
      }
    }

    viewModel.titleData.observe(viewLifecycleOwner) { title ->
      binding.tvTitleMovie.text = title
    }

    viewModel.overviewData.observe(viewLifecycleOwner) { overview ->
      binding.tvDescMovie.text = overview
    }

    viewModel.imageUrlData.observe(viewLifecycleOwner) { imageUrl ->
      Picasso.get().load(imageUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(R.drawable.loading)
        .into(binding.ivImageMovie)
    }

    //--

    val args = MovieDetailsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId

    viewModel.setMovieId(movieId)
    viewModel.start()
  }

  private fun onClickShowReview() {
    val navDirections = viewModel.getNavDirectionsToMovieReviews()
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun onClickShowTrailer() {
    val itn = Intent(requireContext(), MovieTrailerActivity::class.java)
    itn.putExtra("movie_id", viewModel.getMovieId())
    requireActivity().startActivity(itn)
  }

  private fun showErrorMessage(message: String) {
    val navDirections = MovieDetailsFragmentDirections.actionMovieDetailsToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
