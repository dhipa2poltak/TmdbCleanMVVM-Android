package com.dpfht.tmdbcleanmvvm.data.datasource

import com.dpfht.tmdbcleanmvvm.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvvm.domain.entity.TrailerDomain

interface AppDataSource {

  suspend fun getMovieGenre(): GenreDomain

  suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain

  suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain

  suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain

  suspend fun getMovieTrailer(movieId: Int): TrailerDomain
}
