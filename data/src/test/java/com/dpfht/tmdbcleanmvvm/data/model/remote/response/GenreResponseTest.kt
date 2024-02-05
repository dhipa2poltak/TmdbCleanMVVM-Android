package com.dpfht.tmdbcleanmvvm.data.model.remote.response

import com.dpfht.tmdbcleanmvvm.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GenreResponseTest {

  @Test
  fun `ensure converting GenreResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("GenreResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<GenreResponse>() {}.type
    val response = Gson().fromJson<GenreResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.genres.size == 19)
  }
}
