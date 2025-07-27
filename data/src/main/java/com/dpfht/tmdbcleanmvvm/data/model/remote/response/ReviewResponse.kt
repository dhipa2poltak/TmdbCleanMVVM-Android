package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.data.model.remote.ReviewDTO
import com.dpfht.tmdbcleanmvvm.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvvm.domain.model.ReviewModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class ReviewResponse(
    val id: Int? = -1,
    val page: Int? = -1,
    val results: List<ReviewDTO>? = listOf(),

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = -1,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = -1
)

fun ReviewResponse.toDomain(): ReviewModel {
    val reviewEntities = results?.map { it.toDomain() }

    return ReviewModel(reviewEntities?.toList() ?: listOf(), this.page ?: -1)
}
