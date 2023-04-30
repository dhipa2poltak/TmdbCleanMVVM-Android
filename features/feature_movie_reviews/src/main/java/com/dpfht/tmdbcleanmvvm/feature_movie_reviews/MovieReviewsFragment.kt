package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.di.DaggerMovieReviewsComponent
import com.dpfht.tmdbcleanmvvm.framework.R
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.MovieReviewsDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewsFragment: Fragment() {

  private lateinit var binding: FragmentMovieReviewsBinding
  private val viewModel by viewModels<MovieReviewsViewModel>()

  @Inject
  lateinit var navigationService: NavigationInterface

  @Inject
  lateinit var adapter: MovieReviewsAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerMovieReviewsComponent.builder()
      .context(requireContext())
      .dependency(EntryPointAccessors.fromApplication(requireContext().applicationContext, MovieReviewsDependency::class.java))
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieReviewsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.reviews = viewModel.reviews

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = adapter

    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          viewModel.getMovieReviews()
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    //--

    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { value ->
      if (value) {
        binding.pbLoading.visibility = View.VISIBLE
      } else {
        binding.pbLoading.visibility = View.GONE
      }
    }

    viewModel.notifyItemInserted.observe(viewLifecycleOwner) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
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

    //--

    arguments?.let {
      val movieId = it.getInt("movieId")
      val movieTitle = it.getString("movieTitle")

      binding.tvMovieName.text = movieTitle

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }

  private fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}