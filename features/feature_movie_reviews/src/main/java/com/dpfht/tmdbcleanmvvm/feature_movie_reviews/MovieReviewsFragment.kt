package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewsFragment: BaseFragment<FragmentMovieReviewsBinding, MovieReviewsViewModel>(R.layout.fragment_movie_reviews) {

  override val viewModel by viewModels<MovieReviewsViewModel>()

  @Inject
  lateinit var adapter: MovieReviewsAdapter

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

    observeViewModel()

    arguments?.let {
      val movieId = it.getInt("movieId")
      val movieTitle = it.getString("movieTitle")

      binding.tvMovieName.text = movieTitle

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

    viewModel.notifyItemInserted.observe(viewLifecycleOwner) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
      }
    }
  }
}
