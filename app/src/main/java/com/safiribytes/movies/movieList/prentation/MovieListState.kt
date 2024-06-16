package com.safiribytes.movies.movieList.prentation

import com.safiribytes.movies.movieList.data.domain.model.Movie

data class MovieListState(
    val  isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
)