package com.dpfht.tmdbcleanmvvm.core.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class AuthorDetails(
    val name: String? = null,
    val username: String? = null,

    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = null,

    val rating: Float = 0.0f
)
