package com.safiribytes.movies.movieList.data.domain.repository

import com.safiribytes.movies.movieList.data.domain.model.Movie
import com.safiribytes.movies.movieList.data.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun  getMoviesList(
        forceFetchFromRemote:Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}