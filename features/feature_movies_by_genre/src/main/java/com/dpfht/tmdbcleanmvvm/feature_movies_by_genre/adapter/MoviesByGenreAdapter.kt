package com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.framework.R
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.databinding.RowMovieBinding
import com.dpfht.tmdbcleanmvvm.domain.model.Movie
import com.dpfht.tmdbcleanmvvm.feature_movies_by_genre.adapter.MoviesByGenreAdapter.MovieByGenreHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MoviesByGenreAdapter @Inject constructor(

): RecyclerView.Adapter<MovieByGenreHolder>() {

  lateinit var movies: ArrayList<Movie>
  var onClickMovieListener: OnClickMovieListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreHolder {
    val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return MovieByGenreHolder(binding)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: MovieByGenreHolder, position: Int) {
    holder.bindData(movies[position])
    holder.itemView.setOnClickListener {
      onClickMovieListener?.onClickMovie(position)
    }
  }

  class MovieByGenreHolder(private val binding: RowMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(movie: Movie) {
      binding.tvTitleMovie.text = movie.title
      binding.tvOverviewMovie.text = movie.overview

      Picasso.get().load(movie.imageUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(R.drawable.loading)
        .into(binding.ivMovie)
    }
  }

  interface OnClickMovieListener {
    fun onClickMovie(position: Int)
  }
}
