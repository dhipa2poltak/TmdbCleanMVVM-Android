package com.dpfht.tmdbcleanmvvm.feature_genre

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbcleanmvvm.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvvm.feature_genre.databinding.FragmentGenreBinding
import com.dpfht.tmdbcleanmvvm.feature_genre.di.DaggerGenreComponent
import com.dpfht.tmdbcleanmvvm.framework.base.BaseFragment
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.GenreDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment: BaseFragment<GenreViewModel>() {

  private lateinit var binding: FragmentGenreBinding
  override val viewModel by viewModels<GenreViewModel>()

  @Inject
  override lateinit var navigationService: NavigationInterface

  @Inject
  lateinit var adapter: GenreAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerGenreComponent.builder()
      .context(requireContext())
      .dependency(EntryPointAccessors.fromApplication(requireContext().applicationContext, GenreDependency::class.java))
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setToolbar()

    adapter.genres = viewModel.genres

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        val genre = viewModel.genres[position]
        navigationService.navigateToMoviesByGender(genre.id, genre.name)
      }
    }

    observeViewModel()

    viewModel.start()
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

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }
}
