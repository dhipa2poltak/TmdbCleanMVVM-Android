package com.dpfht.tmdbcleanmvvm.feature_movie_reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieReviewsFragment: BaseFragment<FragmentMovieReviewsBinding, MovieReviewsViewModel>(R.layout.fragment_movie_reviews) {

  override val viewModel by viewModels<MovieReviewsViewModel>()

  override fun setupView(view: View, savedInstanceState: Bundle?) {
    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = viewModel.adapter

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
  }

  override fun startViewModel() {
    arguments?.let {
      val movieId = it.getInt("movieId")
      val movieTitle = it.getString("movieTitle")

      binding.tvMovieName.text = movieTitle

      viewModel.setMovieId(movieId)
      viewModel.start()
    }
  }
}
