package com.dpfht.tmdbcleanmvvm.feature_movie_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dpfht.tmdbcleanmvvm.feature_movie_details.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbcleanmvvm.feature_movie_details.di.DaggerMovieDetailsComponent
import com.dpfht.tmdbcleanmvvm.framework.R
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MovieDetailsDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment: BaseFragment<MovieDetailsViewModel>() {

  private lateinit var binding: FragmentMovieDetailsBinding
  override val viewModel by viewModels<MovieDetailsViewModel>()

  @Inject
  override lateinit var navigationService: NavigationInterface

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerMovieDetailsComponent.builder()
      .context(requireContext())
      .dependency(EntryPointAccessors.fromApplication(requireContext().applicationContext, MovieDetailsDependency::class.java))
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationDependency::class.java))
      .build()
      .inject(this)
  }

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

    observeViewModel()

    arguments?.let {
      val movieId = it.getInt("movieId")

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }

  override fun observeViewModel() {
    super.observeViewModel()

    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { value ->
      binding.pbLoading.visibility = if (value) {
        View.VISIBLE
      } else {
        View.GONE
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
  }

  private fun onClickShowReview() {
    navigationService.navigateToMovieReviews(viewModel.getMovieId(), viewModel.getMovieTitle())
  }

  private fun onClickShowTrailer() {
    navigationService.navigateToMovieTrailer(viewModel.getMovieId())
  }
}
