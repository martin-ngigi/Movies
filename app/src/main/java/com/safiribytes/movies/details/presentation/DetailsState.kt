package com.safiribytes.movies.details.presentation

import com.safiribytes.movies.movieList.data.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
