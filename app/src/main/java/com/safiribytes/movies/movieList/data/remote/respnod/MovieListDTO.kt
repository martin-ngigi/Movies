package com.safiribytes.movies.movieList.data.remote.respnod

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)