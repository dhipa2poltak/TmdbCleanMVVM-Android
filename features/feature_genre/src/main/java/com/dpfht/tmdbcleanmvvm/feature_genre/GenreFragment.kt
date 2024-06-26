package com.dpfht.tmdbcleanmvvm.feature_genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbcleanmvvm.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvvm.feature_genre.databinding.FragmentGenreBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment: BaseFragment<FragmentGenreBinding, GenreViewModel>(R.layout.fragment_genre) {

  override val viewModel by viewModels<GenreViewModel>()

  override fun setupView(view: View, savedInstanceState: Bundle?) {
    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = viewModel.adapter

    viewModel.adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        val genre = viewModel.genres[position]
        navigationService.navigateToMoviesByGender(genre.id, genre.name)
      }
    }
  }

  override fun startViewModel() {
    viewModel.start()
  }
}
