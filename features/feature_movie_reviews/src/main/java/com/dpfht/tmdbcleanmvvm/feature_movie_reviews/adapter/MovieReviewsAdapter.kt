package com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvvm.domain.model.Review
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.databinding.RowReviewBinding
import com.dpfht.tmdbcleanmvvm.feature_movie_reviews.adapter.MovieReviewsAdapter.ReviewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieReviewsAdapter @Inject constructor(

): RecyclerView.Adapter<ReviewHolder>() {

  lateinit var reviews: ArrayList<Review>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
    val binding = RowReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ReviewHolder(binding)
  }

  override fun getItemCount(): Int {
    return reviews.size
  }

  override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
    holder.bindData(reviews[position])
  }

  class ReviewHolder(private val binding: RowReviewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(review: Review) {
      binding.tvAuthor.text = review.author
      binding.tvContent.text = review.content

      val imageUrl = review.authorDetails?.avatarPath
      if (imageUrl != null && imageUrl.isNotEmpty()) {
        Picasso.get().load(imageUrl)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(binding.ivAuthor)
      }
    }
  }
}
