package com.dpfht.tmdbcleanmvvm.data.datasource

import com.dpfht.tmdbcleanmvvm.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.GenreModel
import com.dpfht.tmdbcleanmvvm.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvvm.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvvm.domain.model.TrailerModel

interface AppDataSource {

  suspend fun getMovieGenre(): GenreModel

  suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreModel

  suspend fun getMovieDetail(movieId: Int): MovieDetailsModel

  suspend fun getMovieReviews(movieId: Int, page: Int): ReviewModel

  suspend fun getMovieTrailer(movieId: Int): TrailerModel
}
