package com.dpfht.tmdbcleanmvvm.data.model.remote

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvvm.domain.model.AuthorDetails
import com.dpfht.tmdbcleanmvvm.domain.model.Review
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

@Keep
@Suppress("unused")
data class ReviewResp(
    val author: String? = "",

    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetailsResp? = null,

    val content: String? = "",

    @SerializedName("created_at")
    @Expose
    val createdAt: Date? = null,

    val id: String? = "",

    @SerializedName("updated_at")
    @Expose
    val updatedAt: Date? = null,

    val url: String? = ""
)

fun ReviewResp.toDomain(): Review {
    var imageUrl = authorDetails?.avatarPath ?: ""
    if (imageUrl.startsWith("/")) {
        imageUrl = imageUrl.replaceFirst("/", "")
    }

    if (!imageUrl.startsWith("http")) {
        imageUrl = ""
    }

    val authorDetails = AuthorDetails(imageUrl)

    return Review(author ?: "", authorDetails, content ?: "")
}
