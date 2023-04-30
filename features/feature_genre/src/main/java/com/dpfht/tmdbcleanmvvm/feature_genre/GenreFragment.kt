package com.dpfht.tmdbcleanmvvm.feature_genre

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbcleanmvvm.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvvm.feature_genre.databinding.FragmentGenreBinding
import com.dpfht.tmdbcleanmvvm.feature_genre.di.DaggerGenreComponent
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.GenreDependency
import com.dpfht.tmdbcleanmvvm.framework.di.dependency.NavigationDependency
import com.dpfht.tmdbcleanmvvm.framework.navigation.NavigationInterface
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment: Fragment() {

  private lateinit var binding: FragmentGenreBinding
  private val viewModel by viewModels<GenreViewModel>()

  @Inject
  lateinit var navigationService: NavigationInterface

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

    viewModel.start()
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }

  private fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(com.dpfht.tmdbcleanmvvm.framework.R.string.canceled_message))
  }
}
