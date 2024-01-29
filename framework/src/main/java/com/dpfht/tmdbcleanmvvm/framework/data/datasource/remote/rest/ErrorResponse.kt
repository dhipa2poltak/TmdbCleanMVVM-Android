package com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(

  val success: Boolean? = false,
  @SerializedName("status_code")
  val statusCode: Int? = 0,
  @SerializedName("status_message")
  val statusMessage: String? = ""
)