package com.dpfht.tmdbcleanmvvm.core.data.repository

import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvvm.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvvm.core.domain.usecase.UseCaseResultWrapper

interface AppDataSource {

  suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult>

  suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult>

  suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult>

  suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult>

  suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult>
}