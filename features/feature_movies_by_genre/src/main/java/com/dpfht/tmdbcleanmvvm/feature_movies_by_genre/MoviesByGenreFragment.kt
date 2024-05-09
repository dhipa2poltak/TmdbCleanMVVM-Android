package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.adapter.MoviesByGenreAdapter.OnClickMovieListener
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.databinding.FragmentMoviesByGenreBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesByGenreFragment: BaseFragment<FragmentMoviesByGenreBinding, MoviesByGenreViewModel>(R.layout.fragment_movies_by_genre) {

  override val viewModel by viewModels<MoviesByGenreViewModel>()

  override fun setupView(view: View, savedInstanceState: Bundle?) {
    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvMoviesByGenre.layoutManager = layoutManager
    binding.rvMoviesByGenre.adapter = viewModel.adapter

    viewModel.adapter.onClickMovieListener = object : OnClickMovieListener {
      override fun onClickMovie(position: Int) {
        val movie = viewModel.movies[position]
        navigationService.navigateToMovieDetails(movie.id)
      }
    }

    binding.rvMoviesByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          viewModel.getMoviesByGenre()
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })
  }

  override fun startViewModel() {
    arguments?.let {
      val genreId = it.getInt("genreId")
      val genreName = it.getString("genreName")

      val title = "$genreName movies"
      binding.tvTitle.text = title

      viewModel.setGenreId(genreId)
      viewModel.start()
    }
  }
}
